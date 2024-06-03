import React, { useState, useEffect } from 'react';
import FluctuationChart from '../../contents/chart/FluctuationChart';
import KorMap from '../../contents/korMap/KorMap';
import PopulationTable from '../../contents/Table/PopulationTable';
import Header from '../../components/Header';
import './Main.css'; // CSS 파일 import
import Stats from '../../contents/stats/Stats';
import { getFilteredDisabledPeople } from '../../api/api'; // API 호출 함수 가져오기

function Main() {
    const [data, setData] = useState([]);
    const [totalPopulation, setTotalPopulation] = useState(0); // 전체 인구수 상태 추가

    useEffect(() => {
        fetchJobs();
    }, []);

    const fetchJobs = async () => {
        const res = await getFilteredDisabledPeople();
        setData(res);
        // 전체 인구수를 설정
        const nationalPopulation = res.find(item => item.C1_NM === "전국")?.DT;
        setTotalPopulation(nationalPopulation);
    };

    return (
        <div className="wrapper">
            <Header title='한눈에 보는 장애인 고용 통계'/>
            {/* 전체 인구수를 Stats 컴포넌트에 전달 */}
            <Stats onDataReceived={() => totalPopulation} />
            
            {/* 첫 번째 섹션 */}
            <Header title='전국 장애인 인구 현황'/>
            <div className="container">
                <div className="content">
                    <KorMap data={data} />
                </div>
                <div className="content"> 
                    <div className="sub-content"> 
                        <p>전국 인구 테이블</p>
                        <PopulationTable data={data} />
                    </div>
                </div>
            </div>

            {/* 두 번째 섹션 */}
            <Header title='인구수 그래프'/>
            <div className='Big-content'>
                <FluctuationChart data={data} />
            </div>
        </div>
    );
}

export default Main;
