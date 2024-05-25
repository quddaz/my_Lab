import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Job from './component/Jobs/Job';
import reportWebVitals from './reportWebVitals';
import KakaoMap from './component/map/KakaoMap';
import UserAuthChart from './component/chart/UserAuthChart';
import UserBarChart from './component/chart/UserBarChart';
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <KakaoMap />
    <Job/>
    <UserAuthChart/>
    <UserBarChart/>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
