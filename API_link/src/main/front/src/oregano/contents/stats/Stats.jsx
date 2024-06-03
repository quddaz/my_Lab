import React, { useState, useEffect, useCallback } from 'react';
import { getStats } from '../../api/api'; // API 호출 함수 가져오기
import QuarterSelector from './component/QuarterSelector';
import StatsSection from './component/StatsSection';
import StatsAll from './component/StatsAll';
import UserAuthChart from '../chart/UserAuthChart';
import Employment from './component/Employment';
import './Stats.css';

function Stats({ onDataReceived }) {
  const [estimatedCounts, setEstimatedCounts] = useState([]); // 추정수 데이터
  const [averageSalaries, setAverageSalaries] = useState([]); // 3개월 평균 임금 데이터
  const [selectedQuarter, setSelectedQuarter] = useState(''); // 분기 선택 
  const [quarters, setQuarters] = useState([]); // 분기 동적 추가를 위한 상태

  useEffect(() => {
    const fetchStats = async () => {
      const { estimatedCounts, averageSalaries } = await getStats();
      setEstimatedCounts(estimatedCounts);
      setAverageSalaries(averageSalaries);

      const allQuarters = [...new Set([...estimatedCounts, ...averageSalaries].map(item => item.PRD_DE))]; 
      setQuarters(allQuarters);
      if (allQuarters.length > 0) {
        setSelectedQuarter(allQuarters[0]);
      }
    };

    fetchStats();
  }, []);

  const filterData = useCallback((data, quarter, includeAll) => {
    return data.filter(item => {
      if (includeAll) {
        return item.PRD_DE === quarter && item.C1_NM === '전체';
      } else {
        return item.PRD_DE === quarter && item.C1_NM !== '전체';
      }
    });
  }, []);

  const formatNumber = (number) => {
    return Number(number).toLocaleString(); // 1,000,000 형태로 포맷팅
  };

  const formatSalary = (salary) => {
    return Math.round(salary).toLocaleString(); // 소수점 단위 반올림 후 포맷팅
  };

  const filteredEstimatedCounts = filterData(estimatedCounts, selectedQuarter, false);
  const filteredAverageSalaries = filterData(averageSalaries, selectedQuarter, false);
  const allEstimatedCounts = filterData(estimatedCounts, selectedQuarter, true);

  const handleDataReceived = useCallback(onDataReceived, [onDataReceived]);

  return (
    <div className="stats-container">
      <QuarterSelector quarters={quarters} setSelectedQuarter={setSelectedQuarter} />
      <div className="total-container">
        <UserAuthChart data={filteredEstimatedCounts} />
        <div className="stats-all-data">
          <StatsAll title="장애인 근로자(추정 수)" data={allEstimatedCounts} unit="명" formatter={formatNumber} />
          <Employment onDataReceived={handleDataReceived} data={allEstimatedCounts} />
        </div>
      </div>
      <div className="stats-content">
        <StatsSection title="장애인 근로자(추정 수) & 3개월 평균 임금" data={filteredEstimatedCounts} unit="명" formatter={formatNumber} />
        <StatsSection data={filteredAverageSalaries} unit="만원" formatter={formatSalary} />
      </div>
    </div>
  );
}

export default Stats;
