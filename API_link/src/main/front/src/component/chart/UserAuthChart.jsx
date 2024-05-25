import React from 'react';
import styled from 'styled-components';
import { Tooltip } from 'antd';
import { PieChart, Pie, Cell, Legend, Tooltip as RechartsTooltip, ResponsiveContainer } from 'recharts';

const COLORS = [
  '#5893E1', '#FD9179', '#82ca9d', '#8884d8', '#8dd1e1', '#ffb3b3', '#ffcc80', '#ffcc99', '#a6d854', '#c7e9b4',
  '#d9d9d9', '#e31a1c', '#fb9a99', '#fdbf6f', '#ff7f00', '#cab2d6', '#6a3d9a', '#b2df8a', '#33a02c'
];

const data = [
  { region: '서울', population: 4616624, growth: '0.8%' },
  { region: '부산', population: 779568, growth: '1.5%' },
  { region: '대구', population: 511389, growth: '2.5%' },
  { region: '인천', population: 683536, growth: '2.3%' },
  { region: '광주', population: 317475, growth: '1.1%' },
  { region: '대전', population: 424453, growth: '1.4%' },
  { region: '울산', population: 330798, growth: '2.2%' },
  { region: '세종', population: 81430, growth: '4.7%' },
  { region: '경기', population: 3610187, growth: '2.3%' },
  { region: '충북', population: 449168, growth: '2.5%' },
  { region: '충남', population: 605754, growth: '3.5%' },
  { region: '전남', population: 447947, growth: '2.7%' },
  { region: '경북', population: 684795, growth: '1.7%' },
  { region: '경남', population: 851962, growth: '2.8%' },
  { region: '제주', population: 159531, growth: '1.9%' },
  { region: '전북', population: 416147, growth: '2.6%' },
  { region: '강원', population: 359788, growth: '2.1%' },
  { region: '전국', population: 81430, growth: '1.8%' }
];

const UserAuthChart = () => {
  return (
    <StyledWrapper>
      <div className="user-dashboard-card-title small">
        <Tooltip
          title={<CustomTooltip />}
          color={'white'}
          overlayInnerStyle={{
            padding: '16px',
            color: '#000',
            fontSize: '12px',
            borderRadius: '4px',
          }}
        >
          <span className="cursor">지역별 인구 수:</span>
        </Tooltip>
      </div>

      <ChartWrapper>
        <ResponsiveContainer width="100%" height="100%">
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
            <RechartsTooltip content={<UserDashboardChartTooltip />} />
            <Legend />
          </PieChart>
        </ResponsiveContainer>
      </ChartWrapper>
    </StyledWrapper>
  );
};

export default UserAuthChart;

const CustomTooltip = () => {
  const strongStyle = {
    display: 'inline-block',
    fontWeight: 'bold',
  };

  return (
    <div>
      <strong style={strongStyle}>지역별 인구 수</strong>
      <div className="tooltip-content">
        각 지역의 인구 수와 성장률을 나타냅니다.
      </div>
    </div>
  );
};

const UserDashboardChartTooltip = ({ active, payload }) => {
  if (!active || !payload || !payload.length) return null;

  const { region, population, growth } = payload[0].payload;

  return (
    <CustomTooltipWrapper>
      <TooltipItem color={COLORS[payload[0].index % COLORS.length]}>
        <div className="label">{region}</div>
        <strong className="value">{`인구: ${parseInt(population).toLocaleString()}명, 성장률: ${growth}`}</strong>
      </TooltipItem>
    </CustomTooltipWrapper>
  );
};

const StyledWrapper = styled.div`
  width: 100%;
`;

const ChartWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 400px;
`;

const CustomTooltipWrapper = styled.div`
  padding: 16px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 2px 3px 8px #00000022;
`;

const TooltipItem = styled.div`
  display: flex;
  justify-content: left;
  position: relative;
  text-transform: uppercase;

  &::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: ${(props) => `${props.color}`};
  }

  .label {
    margin: 0 16px;
  }

  .value {
    font-weight: bold;
  }
`;
