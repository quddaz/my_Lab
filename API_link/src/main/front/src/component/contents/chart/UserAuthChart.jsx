import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip as RechartsTooltip } from 'recharts';
import styled from 'styled-components';

const COLORS = [
  '#5893E1', '#FD9179', '#82ca9d', '#8884d8', '#8dd1e1', '#ffb3b3', '#ffcc80', '#ffcc99', '#a6d854', '#c7e9b4',
  '#d9d9d9', '#e31a1c', '#fb9a99', '#fdbf6f', '#ff7f00', '#cab2d6', '#6a3d9a', '#b2df8a', '#33a02c'
];

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    const { region, population } = payload[0].payload;
    return (
      <CustomTooltipWrapper>
        <p>{`${region}: ${population.toLocaleString()}`}</p>
      </CustomTooltipWrapper>
    );
  }
  return null;
};

const UserAuthChart = ({ data }) => {
  return (
    <ResponsiveContainer width="40%" height={500}>
      <PieChart>
        <Pie
          dataKey="population"
          data={data}
          innerRadius={60}
          outerRadius={80}
        >
          {data.map((entry, index) => (
            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
          ))}
        </Pie>
        <RechartsTooltip content={<CustomTooltip />} />
      </PieChart>
    </ResponsiveContainer>
  );
};

const CustomTooltipWrapper = styled.div`
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
`;

export default UserAuthChart;
