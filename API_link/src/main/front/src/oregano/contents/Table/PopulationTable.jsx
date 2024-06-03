import React from 'react';
import './PopulationTable.css';

function formatNumber(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function PopulationGraph({ data }) {
  const halfLength = Math.ceil(data.length / 2);
  const firstHalf = data.slice(0, halfLength);
  const secondHalf = data.slice(halfLength);

  return (
    <table className="population-table">
      <thead>
        <tr>
          <th>지역</th>
          <th>인구수</th>
          <th>지역</th>
          <th>인구수</th>
        </tr>
      </thead>
      <tbody>
        {firstHalf.map((item, index) => (
          <tr key={index}>
            <td>{item.C1_NM}</td>
            <td className='DT'>{formatNumber(parseInt(item.DT))}</td>
            {secondHalf[index] && (
              <>
                <td>{secondHalf[index].C1_NM}</td>
                <td className='DT'>{formatNumber(parseInt(secondHalf[index].DT))}</td>
              </>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default PopulationGraph;
