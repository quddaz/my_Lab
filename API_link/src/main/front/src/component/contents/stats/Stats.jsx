import React, { useState, useEffect } from "react";
import axios from "axios";



function Stats(){
    const estimatedCounts = [];
    const averageSalaries = [];
    const [quarter, setQuarter] = useState("2");
    useEffect(() => {
      getStats();
    }, []);
  
    const getStats = () => {
      axios.get(`/EstimatedSixMonthsIncome`) 
        .then((res) => {
          const data = res.data;
          setEstimatedCounts(data["추정 수"]);
          setAverageSalaries(data["3개월 평균 임금"]);
        })
        .catch((error) => {
          console.error("Error fetching statistics:", error);
        });
    }
    return(
    <>
    
    </>
    );
}

export default Stats;