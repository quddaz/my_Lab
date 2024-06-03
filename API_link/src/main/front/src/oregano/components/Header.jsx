import React from "react";
import styled from "styled-components";
const StyledHeader = styled.h1`
  text-align: left;
  font-size: 24px;
  margin-bottom: 20px;
  width: 80%;
`;

const Header = ({ title }) => {
  return (
    <StyledHeader>
      {title}
    </StyledHeader>
  );
};

export default Header;