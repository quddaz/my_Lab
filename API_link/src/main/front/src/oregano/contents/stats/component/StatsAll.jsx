import React from 'react';
import styled from 'styled-components';

const StatsAllContainer = styled.div`
  margin-bottom: 20px;
  width: 48%;
  justify-content:  center;
  display: flex;
  flex-direction: column;
  align-items: center;

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
  font-size: 1.2em;
  font-weight: bold;
  color: #333;
  margin-right: 20px;
  margin-bottom: 10px;

  &:last-child {
    margin-right: 0;
  }
`;

const Value = styled.a`
  font-size: 2.3em;
  text-decoration: underline;
  &:hover {
    text-decoration: underline;
  }
`;

const Unit = styled.a`
  font-size: 1.1em;
  margin-left: 5px;
`;

function StatsAll({ title, data, unit, formatter }) {
  return (
    <StatsAllContainer>
      <img src="/logo02.png" alt="MyLogo" />
      <Title>{title}</Title>
      <StatsSection>
        {data.map((item, index) => (
          <StatItem key={index}>
            <Value>{formatter(item.DT)}</Value>
            <Unit>{unit}</Unit>
          </StatItem>
        ))}
      </StatsSection>
    </StatsAllContainer>
  );
}

export default StatsAll;
