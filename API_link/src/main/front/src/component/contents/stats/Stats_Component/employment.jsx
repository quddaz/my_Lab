import React from 'react';
import styled from 'styled-components';

const StatsAllContainer = styled.div`
  margin-bottom: 20px;
  width: 48%;
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
  text-align: right;
  font-size: 1.2em;
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
  font-size: 2em;
  text-decoration: underline;
  &:hover {
    text-decoration: underline;
  }
`;

function employment({ onDataReceived, data }) {
  const totalPopulation = onDataReceived ? onDataReceived() : 0; // 전체 인구수를 받아옴

  return (
    <StatsAllContainer>
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

export default employment;
