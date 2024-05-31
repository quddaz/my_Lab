import React, { useEffect, useState } from 'react';
import branches from './branches';
import './KakaoMap.css';

const { kakao } = window;

function KakaoMap() {
  const [selectedBranch, setSelectedBranch] = useState('서울지역본부');

  useEffect(() => {
    const mapScript = document.createElement('script');
    mapScript.async = true;
    mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=8f80c29372501ab918f32c6f4da8763f&libraries=services&autoload=false`;
    document.head.appendChild(mapScript);

    mapScript.onload = () => {
      window.kakao.maps.load(() => {
        const container = document.getElementById('map');
        const options = {
          center: new window.kakao.maps.LatLng(33.450701, 126.570667),
          level: 3
        };

        const map = new window.kakao.maps.Map(container, options);
        showMarker(map, selectedBranch);
      });
    };

  }, [selectedBranch]);

  const showMarker = (map, branchName) => {
    const branch = branches.find(branch => branch.name === branchName);
    if (branch) {
      const geocoder = new window.kakao.maps.services.Geocoder();
      geocoder.addressSearch(branch.address, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
          const marker = new window.kakao.maps.Marker({
            map: map,
            position: coords
          });
          const infowindow = new window.kakao.maps.InfoWindow({
            content: `<div style="width:150px;text-align:center;padding:6px 0;">${branch.name}</div>`
          });
          infowindow.open(map, marker);
          map.setCenter(coords);
        }
      });
    }
  };

  const handleBranchChange = (e) => {
    setSelectedBranch(e.target.value);
    updateInfoPanel(e.target.value);
  };

  const updateInfoPanel = (branchName) => {
    const branch = branches.find(branch => branch.name === branchName);
    if (branch) {
      const infoPanel = document.getElementById('infoPanel');
      infoPanel.innerHTML = `
        <div id="panel-container">
          <p class="kakao-map-location">Location</p>
          <h2>${branch.name}</h2>
        </div>
        <div class="kakao-map-details">
          <p>주소: ${branch.address}</p>
          <p>전화: ${branch.tel}</p>
          <p>팩스: ${branch.fax}</p>
          <p>관할구역: ${branch.beat}</p>
        </div>
      `;
    }
  };

  return (
    <div className="kakao-map-container">
      <div className="kakao-map-header">
        <div className="kakao-map-location-heading">오시는 길</div>
        <select className="kakao-map-branch-select" value={selectedBranch} onChange={handleBranchChange}>
          {branches.map(branch => (
            <option key={branch.name} value={branch.name}>{branch.name}</option>
          ))}
        </select>
      </div>
      <div className="kakao-map-sub-container">
        <div id="map" className="kakao-map-map-container"></div>
        <div id="infoPanel" className="kakao-map-info-panel">
          <div id="panel-container">
            <p className="kakao-map-location">Location</p>
            <h2>{branches[0].name}</h2>    
          </div>
          <div className="kakao-map-details">
            <p>주소: {branches[0].address}</p>
            <p>전화: {branches[0].tel}</p>
            <p>팩스: {branches[0].fax}</p>
            <p>관할구역: {branches[0].beat}</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default KakaoMap;
