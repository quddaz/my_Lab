import React from "react";
import './assistanceStyle.css';

function AssistiveDeviceSupport() {
    return (
        <details>
            <summary>보조공학기기지원</summary>
            <header>
                <h1>보조공학기기지원</h1>
            </header>
            <main>
                <section id="section1">
                    <h2>장애인 보조공학기기지원이란</h2>
                    <p>장애인 고용촉진과 직업생활 안정을 위해 맞춤형 보조공학기기, 보조공학기기 구입 및 대여 비용을 지원하는 제도입니다.</p>
                </section>
                <section id="section2">
                    <h2>신청 주체</h2>
                    <ul>
                        <li>장애인을 고용한 사업주 또는 고용하려는 사업주, 국가 및 지방자치단체의 장(공무원이 아닌 근로자 대상)</li>
                        <li>근로자를 고용하지 않거나 4명 이하의 근로자를 고용하고 있는 장애인 사업주(장애인 근로자를 고용하거나 3개월 이내 고용하려는 사업주에 한함)</li>
                        <li>장애인근로자 · 공무원</li>
                    </ul>
                </section>
                <section id="section3">
                    <h2>지원 내용</h2>
                    <ul>
                        <li>장애인 1인당 1,500만 원(중증* 2,000만 원) 한도 지원</li>
                        <li>고용지원 필요도 결정 결과에 따라 추가 지원이 필요한 장애인의 경우 포함</li>
                        <li>지원한도액은 보조공학기기 구입·대여 비용, 맞춤형 보조공학기기 지원액을 합산하여 고용유지기간(2년) 동안 초과 불가, 초과 금액은 본인 부담</li>
                        <li>보조공학기기 상담·평가 결과에 따라 신청내용과 다른 제품이 결정되거나 지원되지 않을 수 있음</li>
                    </ul>
                </section>
                <section id="section4">
                    <h2>신청방법</h2>
                    <ul>
                        <li>보조공학기기 신청서와 구비서류 각 1부</li>
                        <li>사업주용 신청서</li>
                        <li>장애인공무원용 신청서</li>
                        <li>장애인근로자용 신청서</li>
                        <li>작업용 보조공학기기 신청을 위한 사전 정보 기록지</li>
                    </ul>
                </section>
                <section id="section5">
                    <h2>신청기간</h2>
                    <p>연중 예산 소진 시까지</p>
                </section>
            </main>
        </details>
    );
}

export default AssistiveDeviceSupport;
