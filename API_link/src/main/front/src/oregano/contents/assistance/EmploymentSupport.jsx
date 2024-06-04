import React from "react";
import './assistanceStyle.css';

function EmploymentSupport() {
    return (
        <details>
            <summary>고용 지원 필요도 결정 제도</summary>
            <h1>고용지원 필요도 결정</h1>

            <h2>제도 개요</h2>
            <p>고용지원 필요도 결정은 취업과 직업유지를 희망하는 여러분의 고용기초능력, 고용준비태도,
                고용다양성환경을 종합적으로 평가하여 개인 맞춤형 고용지원 방향을 찾을 수 있도록 도와드립니다.</p>
            <table>
                <thead>
                    <tr>
                        <th>영역</th>
                        <th>평가내용</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>고용기초능력</td>
                        <td>감각기능(시각, 청각), 운동기능(손, 팔, 다리 움직임), 지식을 얻고 사용하는 정도</td>
                    </tr>
                    <tr>
                        <td>고용준비태도</td>
                        <td>일상생활에서 요구되는 활동 수행 정도, 자기를 이해하고 다른 사람과 상호작용하는 정도, 일상생활에 영향을 주는 감정의 정도</td>
                    </tr>
                    <tr>
                        <td>고용다양성환경</td>
                        <td>작업장의 환경 제한 정도</td>
                    </tr>
                </tbody>
            </table>
            <h2>고용지원 필요도 결정 과정</h2>
            <table>
                <thead>
                    <tr>
                        <th>항목</th>
                        <th>내용</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>대상</td>
                        <td>공단 고용서비스를 희망하는 15세 이상의 장애인, 특수교육 대상자</td>
                    </tr>
                    <tr>
                        <td>신청방법</td>
                        <td>
                            <p>(오프라인) 관할 지역본부 및 지사 방문 우편 또는 팩스로 신청 가능</p>
                            <p>(온라인) 포털 회원가입 후 신청</p>
                            <p className="alert">* 온라인 신청 시 회원정보에 기재된 주소지 관할 기관으로 접수되며, 평가일정, 방법 등은 공단 담당자와 별도 협의가 이루어집니다</p>
                        </td>
                    </tr>
                    <tr>
                        <td>평가실시</td>
                        <td>
                            <p>평가방법: 개별평가</p>
                            <p>소요시간: 초기상담 및 기본평가 평균 2~3시간 소요</p>
                            <p>평가비용: 무료</p>
                            <p className="alert">* 초기상담과 기본평가로 맞춤 서비스 판단이 어려울 경우, 심화평가, 사례분석회의, 위원회를 추가 실시할 수 있습니다</p>
                        </td>
                    </tr>
                    <tr>
                        <td>결과통보</td>
                        <td>신청서 제출일로부터 30일 이내 결과 안내</td>
                    </tr>
                </tbody>
            </table>

            <h2>서비스 절차</h2>
            <img src="https://www.kead.or.kr/assets/image/diagram/disabledSupport/service01_04_sub2=4.png" alt="서비스 절차" />
        </details>
    );
}

export default EmploymentSupport;
