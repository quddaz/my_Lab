import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Nav = styled.nav`
  position: fixed;
  top: 0;
  width: 100%;
  height: 80px; /* 네비게이션 바의 높이 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ddd;
  background-color: white;
  padding: 1rem 2rem;
  z-index: 1000;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const Logo = styled.div`
  margin-left: 60px;
  display: flex;
  align-items: center;

  img {
    height: 80px;
    margin-right: 1rem;
  }

  a {
    font-size: 1.5rem;
    color: #333;
    font-weight: bold;
    text-decoration: none;
  }
`;

const NavLinks = styled.ul`
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
  margin-right: 60px;
  li {
    margin: 0 1rem;
    a {
      color: ${props => props.blackText ? '#333' : 'black'};
      text-decoration: none;
      font-size: 1.2rem; /* 텍스트 크기 조정 */
      padding: 0.5rem 1rem;
      transition: color 0.3s ease, background-color 0.3s ease;
      border-radius: 10px; /* 라운드 테두리 적용 */
      &:hover {
        color: white;
        background-color: black; /* 호버 시 배경색 변경 */
      }
    }
  }
`;


const ContentWrapper = styled.div`
  margin-top: 100px; 
  background-color:#f4eef4;
`;

const TopMenu = () => {
  return (
    <>
      <Nav>
        <Logo>
          <Link to="/">
            <img src="/oregano.png" alt="MyLogo" />
          </Link>
        </Logo>
        <NavLinks>
          <li><Link to="/" className="btn">Home</Link></li>
          <li><Link to="/FetchJob" className="btn">구인현황검색</Link></li>
          <li><Link to="/Information" className="btn">지원사업정보</Link></li>
        </NavLinks>
      </Nav>
      <ContentWrapper>
      </ContentWrapper>
    </>
  );
};

export default TopMenu;
