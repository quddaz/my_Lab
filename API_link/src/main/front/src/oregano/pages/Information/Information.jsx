import React from "react";
import KakaoMap from "../../contents/kakaoMap/KakaoMap";
import EmploymentSupport from "../../contents/assistance/EmploymentSupport";
import GovermentSupport from "../../contents/assistance/GovermentSupport";
import './Information.css';

function Information() {
    return (
        <div className="information-container">
            <div className="information-header">
                <h1>한국장애인고용공단 지원 정보</h1>
            </div>
            <div className="information-section">
                <div className="information-assistance-section">
                    <EmploymentSupport/>
                    <GovermentSupport/>
                </div>
            </div>
            <div className="information-section">
                <div className="information-textcontainer">
                    <KakaoMap />
                </div>
            </div>
        </div>
    );
}

export default Information;
