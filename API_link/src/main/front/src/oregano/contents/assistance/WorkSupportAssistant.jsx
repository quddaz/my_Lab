import React from "react";
import './assistanceStyle.css';

function WorkSupportAssistant() {
    return (
        <details>
            <summary>근로지원인 지원사업</summary>
            <header>
                <h1>근로지원인 지원사업</h1>
            </header>
            <main>
                <section id="section1">
                    <h2>근로지원인 서비스 제도란</h2>
                    <p>업무에 필요한 핵심 업무 수행능력을 보유하고 있으나 장애로 부수적인 업무를 수행하는데 어려움을 겪고 있는 중증장애인 근로자가 근로지원인의 도움을 받아 업무를 수행할 수 있도록 지원하는 제도입니다.</p>
                </section>
                <section id="section2">
                    <h2>서비스 대상</h2>
                    <ul>
                        <li>중증장애인 근로자 또는 고용지원 필요도 결정을 통해 지원이 필요하다고 판단되는 장애인 근로자</li>
                    </ul>
                    <p>서비스 대상 선정 우대사항</p>
                    <ul>
                        <li>최저임금 이상을 지급받는 장애인 근로자</li>
                        <li>여성 중증장애인 근로자</li>
                        <li>중소기업기본법 제2조제1항에 따른 중소기업에 고용된 중증장애인 근로자</li>
                    </ul>
                </section>
                <section id="section3">
                    <h2>서비스 제외 대상</h2>
                    <ul>
                        <li>월 소정근로시간이 60시간 미만인 장애인근로자</li>
                        <li>최저임금 미만을 지급받는 장애인근로자 중 최저임금 적용제외 인가를 받지 않은 자</li>
                        <li>고용관리비용 지원을 받고 있는 장애인근로자</li>
                    </ul>
                </section>
                <section id="section4">
                    <h2>지원 한도</h2>
                    <p>일 8시간, 주 40시간 한도 내 지원</p>
                </section>
                <section id="section5">
                    <h2>서비스 조건 및 기간</h2>
                    <ul>
                        <li>근로지원인 서비스를 이용하는 중증장애인 근로자는 시간당 300원의 본인부담금을 사업수행기관에 납부해야 함</li>
                        <li>매 회계연도 기준 1년 이내</li>
                        <li>서비스 제공기간이 종료된 장애인근로자가 계속 희망할 경우 다시 신청 가능</li>
                    </ul>
                </section>
                <section id="section6">
                    <h2>서비스 절차</h2>
                    <img src="https://www.kead.or.kr/assets/image/diagram/disabledSupport/service03_08_sub1=15.png" alt="서비스 절차" />
                </section>
                <section id="section7">
                    <h2>서비스 신청</h2>
                    <ul>
                        <li>사업주 동의를 받아 공단에 신청</li>
                        <li>중증장애인 기준에 적합함을 인정할 수 있는 서류</li>
                        <li>임금이 최저임금액 이상이라는 사실을 확인할 수 있는 서류 (근로계약서, 임금대장 등)</li>
                    </ul>
                </section>
            </main>
        </details>
    );
}

export default WorkSupportAssistant;
