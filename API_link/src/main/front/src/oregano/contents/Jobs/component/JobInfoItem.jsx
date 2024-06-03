import React from 'react';
import styled from 'styled-components';

const JobInfoItemWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
  
  &:last-child {
    border-bottom: none;
  }
`;

const JobInfoTitle = styled.span`
  font-weight: bold;
  color: #333;
  margin-right: 10px;
  flex-shrink: 0;
`;

const JobInfoContent = styled.span`
  color: #666;
  flex-grow: 1;
`;

function JobInfoItem({ title, content }) {
  return (
    <JobInfoItemWrapper>
      <JobInfoTitle>{title}:</JobInfoTitle>
      <JobInfoContent>{content}</JobInfoContent>
    </JobInfoItemWrapper>
  );
}

export default JobInfoItem;
