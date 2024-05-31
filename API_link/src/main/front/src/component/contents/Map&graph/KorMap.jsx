import React from "react";
import styled from "styled-components";
import { SimpleSouthKoreaMapChart } from "react-simple-south-korea-map-chart";


const Container = styled.div`
  margin-top: 20px;
`;

// 커스텀 툴팁 컴포넌트 정의
const CustomTooltip = ({ darkMode, tooltipStyle, children }) => {
  // 숫자를 1,000 형식으로 포맷팅하는 함수
  const formatNumber = (number) => {
    return number.toLocaleString();
  };

  return (
    <div
      style={{
        borderRadius: 0,
        color: "black",
        position: "fixed",
        minWidth: "80px",
        padding: "10px",
        backgroundColor: "white",
        border: `1px solid ${darkMode ? "#000000" : "#f5f5f5"}`,
        ...tooltipStyle,
      }}
    >
      {children && (
        <div>
          <span style={{ fontWeight: "bold" }}>
            {children.split(":")[0]}
          </span>
          : {formatNumber(parseInt(children.split(":")[1]))}명
        </div>
      )}
    </div>
  );
};
function KorMap({ data }) {
  const processData = (data) => {
    // 데이터 매핑 함수
    const mapData = {
      "전북특별자치도": "전라북도",
      "강원특별자치도": "강원도"
    };

    // 전국 데이터 필터링 및 매핑
    const filteredData = data.filter((item) => item.C1_NM !== "전국").map((item) => ({
      locale: mapData[item.C1_NM] ? mapData[item.C1_NM] : item.C1_NM,
      count: parseInt(item.DT),
    }));

    return filteredData;
  };

  const chartData = processData(data);

  const setColorByCount = (count) => {
    if (count === 0) return "#F1F1F1"; // 흰색
    if (count > 500000) return "#FF5733"; // 진한 빨간색
    if (count > 300000) return "#FF875E"; // 연한 빨간색
    if (count > 100000) return "#FFAB99"; // 연한 주황색
    if (count > 50000) return "#FFCECC"; // 연한 분홍색
    if (count > 25000) return "#FFE7E5"; // 연한 핑크색
    if (count > 10000) return "#FFFAFA"; // 아이보리색
    if (count > 5) return "#FAEBE0"; // 연한 베이지색
    else return "#ebfffd"; // 연한 청록색
  };

  return (
    <Container>
    <SimpleSouthKoreaMapChart
      setColorByCount={setColorByCount}
      data={chartData}
      customTooltip={<CustomTooltip />} // 커스텀 툴팁 적용
    />
    </Container>
  );
}

export default KorMap;
