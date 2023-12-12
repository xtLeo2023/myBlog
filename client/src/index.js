// index.js
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import locale from 'antd/lib/locale/zh_CN';
import { Provider } from 'react-redux';
import store from './redux/store.js';
import './index.css';
import App from './App.js';
import Login from './LoginRegister';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
      <ConfigProvider locale={locale}>
       <Provider store={store}>
           <Routes>
             <Route path="/" element={<Login />} />
             <Route path="/blog/*" element={<App />} />
           </Routes>
      </Provider>
      </ConfigProvider>
    </Router>
);

reportWebVitals();
