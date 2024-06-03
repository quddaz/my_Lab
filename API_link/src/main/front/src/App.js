import React from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";
import FetchJob from './oregano/pages/FetchJob/FetchJob';
import Information from './oregano/pages/Information/Information';
import MainPage from "./oregano/pages/Main/Main";
import TopMenu from "./oregano/layouts/TopMenu";

function App() {
    return (
        <BrowserRouter>
            <TopMenu/>
            <Routes>
                <Route index element={<MainPage />} />
                <Route path="FetchJob" element={<FetchJob />} />
                <Route path="Information" element={<Information />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;