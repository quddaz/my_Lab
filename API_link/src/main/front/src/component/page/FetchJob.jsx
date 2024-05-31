import React from "react";
import Job from "../contents/Jobs/Job";
import { useNavigate } from "react-router-dom";
import Button from '../UI/Button';
import './css/FetchJob.css'; // 새로운 CSS 파일 import

function FetchJob() {
    const navigate = useNavigate();
    return (
        <div className="fetch-job-container">
            <h2 className="fetch-job-header">장애인 고용포털 - 워크투게더(Work Together)</h2>
            <div className="fetch-job-section">
                <div className="fetch-job-textcontainer">
                    <p>장애인 고용포털 「Work Together」 는 장애인, 사업주, 공단 직원뿐만 아니라 장애인 고용문제 관심을 가지고 있는 장애인 가족과 비장애인들까지 장애인 고용과 관련된 모든 요구(need)를 원스톱으로 처리할 수 있는 전문 포털을 지향하고 있는 국가 플렛폼입니다.</p>
                    <a href="https://www.worktogether.or.kr/main.do">Work Together 바로가기</a>
                </div>
            </div>
            <div className="fetch-job-section">
                <h2 className="fetch-job-title">현재 구인공고 보기</h2>
                <Job />
            </div>
        </div>
    );
}

export default FetchJob;
