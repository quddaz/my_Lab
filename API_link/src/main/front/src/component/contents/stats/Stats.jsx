import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Stats.css';
import QuarterSelector from './Stats_Component/QuarterSelector';
import StatsSection from './Stats_Component/StatsSection';
import StatsAll from './Stats_Component/StatsAll';
import UserAuthChart from '../chart/UserAuthChart';
import Employment from './Stats_Component/employment';

function Stats({ onDataReceived }) {
  const [estimatedCounts, setEstimatedCounts] = useState([]);
  const [averageSalaries, setAverageSalaries] = useState([]);
  const [selectedQuarter, setSelectedQuarter] = useState('');
  const [quarters, setQuarters] = useState([]);

  useEffect(() => {
    const getStats = async () => {
      try {
        const res = await axios.get('/EstimatedSixMonthsIncome');
        const data = res.data;
        setEstimatedCounts(data['추정 수']);
        setAverageSalaries(data['3개월 평균 임금']);

        const allQuarters = [...new Set([...data['추정 수'], ...data['3개월 평균 임금']].map(item => item.PRD_DE))];
        setQuarters(allQuarters);
        if (allQuarters.length > 0) {
          setSelectedQuarter(allQuarters[0]);
        }
      } catch (error) {
        console.error('Error fetching statistics:', error);
      }
    };

    getStats();
  }, []);

  const filterData = (data, quarter, includeAll) => {
    return data.filter(item => {
      if (includeAll) {
        return item.PRD_DE === quarter && item.C1_NM === '전체';
      } else {
        return item.PRD_DE === quarter && item.C1_NM !== '전체';
      }
    });
  };

  const formatNumber = (number) => {
    return Number(number).toLocaleString(); // 1,000,000 형태로 포맷팅
  };

  const formatSalary = (salary) => {
    return Math.round(salary).toLocaleString(); // 소수점 단위 반올림 후 포맷팅
  };

  const filteredEstimatedCounts = filterData(estimatedCounts, selectedQuarter, false);
  const filteredAverageSalaries = filterData(averageSalaries, selectedQuarter, false);
  const allEstimatedCounts = filterData(estimatedCounts, selectedQuarter, true);

  return (
    <div className="stats-container">
      <QuarterSelector quarters={quarters} setSelectedQuarter={setSelectedQuarter} />
      <div className='total-container'>
        <UserAuthChart data={filteredEstimatedCounts} />
        <div className='stats-Alldata'>
          <StatsAll title="장애인 근로자(추정 수)" data={allEstimatedCounts} unit="명" formatter={formatNumber} />
          <Employment onDataReceived={onDataReceived} data={allEstimatedCounts} />
        </div>
      </div>
      <div className="stats">
        <StatsSection title="장애인 근로자(추정 수)" data={filteredEstimatedCounts} unit="명" formatter={formatNumber} />
        <StatsSection title="3개월 평균 임금" data={filteredAverageSalaries} unit="만원" formatter={formatSalary} />
      </div>
    </div>
  );
}

export default Stats;
