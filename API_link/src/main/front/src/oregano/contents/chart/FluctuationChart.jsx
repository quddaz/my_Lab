import React from 'react';
import styled from 'styled-components';
import {
  ResponsiveContainer,
  ComposedChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar,
} from 'recharts';

// 지역 이름 표준화
const abbreviateRegion = (regionName) => {
  const abbreviationMap = {
    "서울특별시": "서울",
    "부산광역시": "부산",
    "대구광역시": "대구",
    "인천광역시": "인천",
    "광주광역시": "광주",
    "대전광역시": "대전",
    "울산광역시": "울산",
    "세종특별자치시": "세종",
    "경기도": "경기",
    "강원특별자치도": "강원",
    "충청북도": "충북",
    "충청남도": "충남",
    "전북특별자치도": "전북",
    "전라남도": "전남",
    "경상북도": "경북",
    "경상남도": "경남",
    "제주특별자치도": "제주"
  };

  return abbreviationMap[regionName] || regionName;
};

// 숫자 값을 1,000,000 형식으로 변환
const tickFormatY = (tickItem) => {
  return tickItem >= 1000 ? tickItem.toLocaleString() : tickItem;
};

const tooltipFormat= (value) => {
  return value >= 1000 ? value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') : value;
};

const FluctuationChart = ({ data }) => {
  // "전국"을 제외한 데이터 필터링
  const filteredData = data.filter(entry => entry.C1_NM !== "전국");

  return (
    <StyledWrapper>
      <ResponsiveContainer width="100%" height="100%">
        <ComposedChart
          data={filteredData}
          margin={{
            top: 20,
            left: 30,
            right: -10,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="C1_NM"
            interval={0}
            tickLine={false}
            axisLine={false}
            tick={{ fontSize: 10 }}
            tickFormatter={(value) => abbreviateRegion(value)} // Use abbreviations for tick labels
          />
          <YAxis
            yAxisId="left"
            orientation="left"
            interval={0}
            tickLine={false}
            axisLine={false}
            domain={[0, 700000]}
            tickFormatter={tickFormatY}
          />
          <Tooltip
            labelFormatter={(value) => abbreviateRegion(value)} // Use abbreviations for tooltip labels
            formatter={tooltipFormat}
          />
          <Legend />
          <Bar yAxisId="left" dataKey="DT" name="장애인 인구 수" barSize={20} fill="#8884d8" />
        </ComposedChart>
      </ResponsiveContainer>
    </StyledWrapper>
  );
};

export default FluctuationChart;

const StyledWrapper = styled.div`
  height: 400px;
  width: 1000px;
  margin: 0 auto;
`;
