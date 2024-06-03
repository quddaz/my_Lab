import React from 'react';
import styled from 'styled-components';

const Button = ({ title, onClick }) => {
  return (
    <StyledButton onClick={onClick}>
      {title}
    </StyledButton>
  );
};

const StyledButton = styled.button`
  padding: 8px 16px;
  font-size: 16px;
  border: 1px solid black;
  border-radius: 4px;
  background-color: white;
  color: black; 
  cursor: pointer;
  transition: background-color 0.3s;
  &:hover {
    background-color: wheat;
  }
`;

export default Button;