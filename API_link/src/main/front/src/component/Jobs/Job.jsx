import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Job.css";

const regions = ['서울특별시','경기도','강원도','전라남도', '전라북도', '충청남도', '충청북도', '경상남도', '경상북도', '인천광역시', '광주광역시', '부산광역시', '대전광역시'];
const empTypes = ['상용직', '계약직'];


function Job() {
    const [jobList, setJobList] = useState([]); 
    const [region, setRegion] = useState(""); 
    const [empType, setEmpType] =  useState("");
    useEffect(() => {
        fetchJobs();
    }, [region,empType]);

    const fetchJobs = () => {
        axios.get(`/jobs?region=${region}&empType=${empType}`)
            .then((res) => {
                setJobList(res.data.item);
            })
            .catch((error) => {
                console.error("Error fetching job listings:", error);
            });
    };

    const handleRegionChange = (event) => {
        setRegion(event.target.value);
    };
    const handleEmpTypeChange = (event) => {
        setEmpType(event.target.value);
    };



    return (
        <div className="job-board-container">
            <select className="select-region" value={region} onChange={handleRegionChange}>
                {regions.map(regi => (
                    <option key={regi} value={regi}>{regi}</option>
                ))}
            </select>
            <select className="select-region" value={empType} onChange={handleEmpTypeChange}>
                {empTypes.map(emp => (
                    <option key={emp} value={emp}>{emp}</option>
                ))}
            </select>
            <ul className="job-list">
                {jobList.map((job, index) => (
                    <li className="job-item" key={index}>
                        <div className="job-title">{job.busplaName} : {job.jobNm}</div>
                        <div className="job-date">모집 기간: {job.termDate}</div>
                        <div className="job-detail">문의 연락처: {job.cntctNo}</div>
                        <div className="job-detail">주소: {job.compAddr}</div>
                        <div className="job-detail">고용형태: {job.empType}</div>
                        <div className="job-detail">필요경력: {job.reqCareer}</div>
                        <div className="job-detail">자격조건: {job.reqEduc}</div>
                        <div className="job-detail">{job.salaryType}: {job.salary}</div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Job;