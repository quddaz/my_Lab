import React from "react";
import styled from "styled-components";
const StyledHeader = styled.h1`
  text-align: left;
  font-size: 24px;
  margin-bottom: 20px;
  width: 80%; /* 부모 요소의 너비를 100% 차지 */
`;

const Header = ({ title }) => {
  return (
    <StyledHeader>
      {title}
    </StyledHeader>
  );
};

export default Header;