import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip as RechartsTooltip, Legend } from 'recharts';
import styled from 'styled-components';

const COLORS = ['#5893E1', '#FD9179', '#82ca9d'];

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    const { name, value } = payload[0];
    return (
      <CustomTooltipWrapper>
        <p>{`${name}: ${formatNumber(value)}명`}</p>
      </CustomTooltipWrapper>
    );
  }
  return null;
};

const UserAuthChart = ({ data }) => {
  const formattedData = data.map(item => ({
    name: item.C1_NM,
    value: parseInt(item.DT, 10)
  }));

  return (
    <ChartContainer>
      <LegendContainer>
        <h2>장애인 근로자 추정 수</h2>
        <h3>근무 형태별</h3>
        {formattedData.map((entry, index) => (
          <LegendItem key={`legend-${index}`} color={COLORS[index % COLORS.length]}>
            {`${entry.name}: ${formatNumber(entry.value)}명`}
          </LegendItem>
        ))}
      </LegendContainer>
      <ChartWrapper>
        <PieChart width={350} height={400}>
          <Pie
            dataKey="value"
            data={formattedData}
            innerRadius={70}
            outerRadius={110}
            label
          >
            {formattedData.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
          <RechartsTooltip content={<CustomTooltip />} wrapperStyle={{ display: 'none' }} />
        </PieChart>
      </ChartWrapper>
    </ChartContainer>
  );
};

const ChartContainer = styled.div`
  width: 48%;
  display: flex;
  align-items: center;
  flex-direction: row;
  align-items: flex-start; 
  border-radius: 10px; /* 모서리를 둥글게 만듭니다. */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5); /* 그림자를 추가합니다. */
`;

const LegendContainer = styled.div`
  margin-left: 10px;
  display: flex;
  flex-direction: column;
`;

const ChartWrapper = styled.div`
  margin-left: -50px;
  flex: 0 0 auto;
`;

const LegendItem = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 5px;

  &:before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: ${props => props.color};
    margin-right: 5px;
  }
`;

const CustomTooltipWrapper = styled.div`
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
`;

const formatNumber = (number) => {
  return Number(number).toLocaleString();
};

export default UserAuthChart;
