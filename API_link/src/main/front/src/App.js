import React from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";
import FetchJob from './component/page/FetchJob';
import Information from './component/page/Information';
import MainPage from "./component/page/MainPage";
import TopMenu from "./component/UI/TopMenu";

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