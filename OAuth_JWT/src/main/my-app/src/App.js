import React, { useState } from 'react';
import axios from 'axios';

const onNaverLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/naver";
}

function App() {
    const [userEmail, setUserEmail] = useState(null); // 사용자 이메일을 저장할 상태
    const [loading, setLoading] = useState(false); // 데이터 로딩 상태
    const [error, setError] = useState(null); // 에러 상태

    // 사용자 정보를 요청하는 함수
    const fetchUserData = () => {
        setLoading(true); // 로딩 시작
        setError(null); // 에러 초기화
        axios
            .get("http://localhost:8080/user", { withCredentials: true }) // 사용자 정보를 가져올 엔드포인트
            .then((res) => {
                setUserEmail(res.data); // 사용자 이메일을 상태에 저장
                setLoading(false); // 로딩 완료
            })
            .catch((error) => {
                console.error('Error fetching user data:', error);
                setUserEmail(null); // 에러가 나면 사용자 이메일 상태를 null로 설정
                setLoading(false); // 로딩 완료 상태로 변경
                setError('Error fetching user data.'); // 에러 메시지 설정
            });
    };

    return (
        <>
            <h1>Login</h1>
            <button onClick={onNaverLogin}>Naver Login</button>
            <button onClick={fetchUserData}>Load User Data</button> {/* 사용자 데이터 로드 버튼 */}

            {loading ? (
                <p>Loading...</p> // 데이터 로딩 중 표시
            ) : error ? (
                <p>{error}</p> // 에러 메시지 표시
            ) : userEmail ? (
                <div>
                    <h2>Welcome, {userEmail}!</h2> {/* 사용자 이메일 표시 */}
                </div>
            ) : (
                <p>Please log in.</p>
            )}
        </>
    );
}

export default App;
