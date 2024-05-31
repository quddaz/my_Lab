import React, { useState, useEffect } from 'react';
import axios from 'axios';
import FluctuationChart from '../contents/chart/FluctuationChart';
import { useNavigate } from "react-router-dom";
import KorMap from '../contents/Map&graph/KorMap';
import PopulationTable from '../contents/Map&graph/PopulationTable';
import Header from '../UI/Header';
import './css/MainPage.css'; // CSS 파일 import

function MainPage() {
    const navigate = useNavigate();
    const [data, setData] = useState([]);

    useEffect(() => {
        fetchJobs();
    }, []);

    const fetchJobs = async () => {
        try {
            const res = await axios.get('/filtered-disabled-people');
            setData(res.data);
        } catch (error) {
            console.error("Error fetching job listings:", error);
        }
    };

    return (
        <div className="wrapper">

            {/* 첫 번째 섹션 */}
            <Header title='장애인 인구 현황'/>
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

export default MainPage;
