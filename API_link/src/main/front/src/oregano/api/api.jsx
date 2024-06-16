import axios from 'axios';
// .env 파일에서 기본 URL을 가져옵니다.
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

// Axios 인스턴스를 생성합니다.
const api = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true, // 인증 정보 포함
});

// Main 페이지 상단 스테이터스 데이터 요청
export const getStats = async () => {
  try {
    const res = await api.get('/EstimatedSixMonthsIncome');
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
    const res = await api.get('/filtered-disabled-people');
    return res.data;
  } catch (error) {
    console.error("Error fetching filtered disabled people:", error);
    throw error;
  }
};

// 한국장애인고용공단 실시간 구인구직 API 데이터 요청
export const getFetchJobListings = async (region, empType) => {
  try {
    const res = await api.get('/fetchJobListings', {
      params: { region, empType }
    });
    return res.data.item;
  } catch (error) {
    console.error("Error fetching job listings:", error);
    throw error;
  }
};
