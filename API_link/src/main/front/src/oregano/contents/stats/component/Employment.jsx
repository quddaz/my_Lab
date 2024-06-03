import React from 'react';
import styled from 'styled-components';

const StatsAllContainer = styled.div`
  margin-bottom: 20px;
  width: 48%;
  display: flex;
  flex-direction: column;
  justify-content:  center;
  align-items: center;
  margin-top: -15px;
`;

const Title = styled.h3`
  margin: 0;
`;

const StatsSection = styled.div`
  display: flex;
  flex-wrap: wrap;
`;

const StatItem = styled.div`
  padding: 10px 0;
  text-align: center;
  font-size: 1.5em;
  font-weight: bold;
  color: #333;
  margin-right: 20px;
  margin-bottom: 10px;
  border-radius: 5px;
  background-color: #f9f9f9;

  &:last-child {
    margin-right: 0;
  }
`;

const Value = styled.span`
  font-size: 1.8em;
  text-decoration: underline;
  &:hover {
    text-decoration: underline;
  }
`;

function Employment({ onDataReceived, data }) {
  const totalPopulation = onDataReceived ? onDataReceived() : 3000000; // 전체 인구수를 받아옴

  return (
    <StatsAllContainer>
      <img src="/logo01.png" alt="MyLogo" />
      <Title>장애인고용률(추정치)</Title>
      <StatsSection>
        {data.map((item, index) => (
          <StatItem key={index}>
            <Value>{((item.DT / totalPopulation) * 100).toFixed(2)}%</Value>
          </StatItem>
        ))}
      </StatsSection>
    </StatsAllContainer>
  );
}

export default Employment;
