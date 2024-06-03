import axios from 'axios';

// Main 페이지 상단 스테이터스 데이터 요청
export const getStats = async () => {
  try {
    const res = await axios.get('/EstimatedSixMonthsIncome');
    const data = res.data;
    return {
      estimatedCounts: data['추정 수'],
      averageSalaries: data['3개월 평균 임금'],
    };
  } catch (error) {
    console.error('Error fetching statistics:', error);
    throw error;
  }
};

// 장애인 전체 인구수 데이터 요청
export const getFilteredDisabledPeople = async () => {
  try {
    const res = await axios.get('/filtered-disabled-people');
    return res.data;
  } catch (error) {
    console.error("Error fetching filtered disabled people:", error);
    throw error;
  }
};


// 한국장애인고용공단 실시간 구인구직 API 데이터 요청
export const getFetchJobListings = async (region, empType) => {
  try {
      const res = await axios.get(`/fetchJobListings?region=${region}&empType=${empType}`);
      return res.data.item;
  } catch (error) {
      console.error("Error fetching job listings:", error);
      throw error;
  }
};
