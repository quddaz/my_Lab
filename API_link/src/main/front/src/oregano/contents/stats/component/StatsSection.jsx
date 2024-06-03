import React from 'react';
import styled from 'styled-components';
const SectionContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const SectionTitle = styled.h3`
  margin-bottom: 5px;
  width: 100%;
  margin-left: -10px;
`;

const StatsContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center; 
`;

const StatItem = styled.div`
  width: 30%;
  margin: 10px;
  text-align: center; 
  align-items: center;
`;

const Name = styled.p`
  font-size: 1.0em;
  margin-bottom: 5px;
  font-weight: 700;
  text-align: left;
`;

const Value = styled.a`
  font-size: 2.5em; 
  border-bottom: 2px solid #333;
  font-weight: bold;
`;

const Unit = styled.a`
  font-size: 1.3em;
  font-weight: 700;
`;

function StatsSection({ title, data, unit, formatter }) {
  return (
    <SectionContainer>
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
    </SectionContainer>
  );
}

export default StatsSection;
