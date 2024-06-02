import React from 'react';
import styled from 'styled-components';

const Button = styled.button`
  padding: 10px 20px;
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #f9f9f9;
    color : black;
  }
`;

const QuarterSelectorContainer = styled.div`
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
`;

const QuarterSelector = ({ quarters, setSelectedQuarter }) => {
  return (
    <QuarterSelectorContainer>
      {quarters.map((quarter, index) => (
        <Button key={index} onClick={() => setSelectedQuarter(quarter)}>
          {quarter}
        </Button>
      ))}
    </QuarterSelectorContainer>
  );
};

export default QuarterSelector;
