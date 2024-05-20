import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Job.css";

function Job() {
    const [jobList, setJobList] = useState([]); 
    const [region, setRegion] = useState("서울특별시"); 
    const [page, setPage] = useState(1);

    useEffect(() => {
        fetchJobs();
    }, [page, region]);

    const fetchJobs = () => {
        axios.get(`/jobs?page=${page}&local=${region}`)
            .then((res) => {
                setJobList(res.data.item);
            })
            .catch((error) => {
                console.error("Error fetching job listings:", error);
            });
    };

    const handleRegionChange = (event) => {
        setRegion(event.target.value);
        setPage(1); // Reset page to 1 when region changes
    };

    const handlePageChange = () => {
        setPage(page + 1); // Increment page number to fetch next page of jobs
    };

    return (
        <div className="job-board-container">
            <select className="select-region" value={region} onChange={handleRegionChange}>
                <option value="">All Regions</option>
                <option value="서울특별시">서울특별시</option>
                <option value="경기도">경기도</option>
            </select>
            <button className="load-more-button" onClick={handlePageChange}>Load More</button>

            <ul className="job-list">
                {jobList.map((job, index) => (
                    <li className="job-item" key={index}>
                        <div className="job-title">{job.jobNm}</div>
                        <div className="job-detail">Company: {job.busplaName}</div>
                        <div className="job-detail">Contact: {job.cntctNo}</div>
                        <div className="job-detail">Address: {job.compAddr}</div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Job;
