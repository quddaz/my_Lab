import React from 'react';
import styled from 'styled-components';

const SectionTitle = styled.h3`
  margin-bottom: 5px;
`;

const StatsContainer = styled.div`
  width: 80%;
  display: flex;
  justify-content: center;
  align-items: center; /* 세로 중앙 정렬 추가 */
`;

const StatItem = styled.div`
  width: 32%;
  margin: 10px;
  text-align: left; 
  align-items: center;
`;

const Name = styled.p`
  font-size: 1.2em;
  margin-bottom: 5px;
  font-weight: 700;
`;

const Value = styled.a`
  font-size: 2em; 
  border-bottom: 2px solid #333;
  font-weight: bold; /* 중간 굵기 */
`;

const Unit = styled.a`
  font-size: 1em;
`;

function StatsSection({ title, data, unit, formatter }) {
  return (
    <>
      <SectionTitle>{title}</SectionTitle>
      <StatsContainer>
        {data.map((item, index) => (
          <StatItem key={index}>
            <Name>{item.C1_NM}</Name>
            <Value>{formatter(item.DT)}</Value>
            <Unit>{unit}</Unit>
          </StatItem>
        ))}
      </StatsContainer>
    </>
  );
}

export default StatsSection;
