import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Naver 로그인 페이지로 리디렉션
const onNaverLogin = () => {
    window.location.href = "http://localhost:8080/login/oauth2/authorization/naver"; // Naver 로그인으로 리디렉션
}

function App() {
    const [accessToken, setAccessToken] = useState(null); // Access 토큰 상태
    const [loading, setLoading] = useState(false); // 로딩 상태
    const [error, setError] = useState(null); // 에러 상태

    // 새로운 Access 토큰을 요청하는 함수
    const handleReissueToken = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await axios.post("http://localhost:8080/reissue", {}, {
                withCredentials: true, // 리프레시 토큰 쿠키를 포함

            });

            // 서버가 Access 토큰을 헤더로 반환한다고 가정
            const newAccessToken = response.headers['access']; // 'Access' 헤더에서 토큰 가져오기

            if (newAccessToken) {
                setAccessToken(newAccessToken); // 새로운 Access 토큰 상태에 저장
                localStorage.setItem('Access', newAccessToken); // 로컬 스토리지에 저장
                console.log("New Access Token:", newAccessToken);
            } else {
                console.error("Failed to reissue Access token. No token received.");
                setError("Failed to reissue Access token.");
            }
        } catch (error) {
            console.error("Error reissuing Access token:", error.response ? error.response.data : error.message);
            setError("Error reissuing Access token.");
        } finally {
            setLoading(false);
        }
    };

    // 페이지 로드 시 Access 토큰을 로컬 스토리지에서 읽어 상태에 저장
    useEffect(() => {
        const token = localStorage.getItem('Access');
        if (token) {
            setAccessToken(token);
        }
    }, []);

    return (
        <>
            <h1>Token Management</h1>
            <button onClick={onNaverLogin}>Naver Login</button>
            <button onClick={handleReissueToken} disabled={loading}>
                {loading ? "Reissuing Token..." : "Reissue Access Token"}
            </button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {accessToken ? (
                <div>
                    <h2>Current Access Token:</h2>
                    <p>{accessToken}</p>
                </div>
            ) : (
                <p>No Access token available. Please reissue.</p>
            )}
        </>
    );
}

export default App;
