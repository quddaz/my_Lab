import React from "react";
import KakaoMap from "../contents/Kakao/KakaoMap";
import { useNavigate } from "react-router-dom";
import Button from '../UI/Button';
import Header from '../UI/Header';
function Information(){
    const navigate = useNavigate();
    return (
        <div>
            <KakaoMap/>
        </div>
    );
}

export default Information;