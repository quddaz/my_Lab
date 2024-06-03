import React from 'react';
import styled from 'styled-components';

const Button = styled.button`
  padding: 15px 40px;
  margin-left: 10px;
  background-color: white;
  color: black;
  border: none;
  border-radius: 5px;
  font-size: 13px;
  font-weight: bold;
  border: 1px solid #77e480;
  cursor: pointer;
  transition: background-color 0.3s ease;
  &:hover {
    background-color: #77e480;
    color: white;
  }
`;

const QuarterSelectorContainer = styled.div`
  width: 100%;
  display: flex;
  align-items: end;
  justify-content: right;
  gap: 10px;
  margin-bottom: 10px;
`;

const formatQuarter = (quarter) => { //202302 => 2023년 02분기로 변경
  const year = quarter.substring(0, 4);
  const quarterNumber = quarter.substring(4);
  return `${year}년 ${quarterNumber}분기`;
};

const QuarterSelector = ({ quarters, setSelectedQuarter }) => {
  return (
    <QuarterSelectorContainer>
      {quarters.map((quarter, index) => (
        <Button key={index} onClick={() => setSelectedQuarter(quarter)}>
          {formatQuarter(quarter)}
        </Button>
      ))}
    </QuarterSelectorContainer>
  );
};

export default QuarterSelector;
