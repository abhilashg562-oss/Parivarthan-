import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import UserDashboard from './components/UserDashboard';
import WorkerDashboard from './components/WorkerDashboard';
import './App.css';

// Landing Page Component with New Design
function LandingPage() {
  const navigate = useNavigate();

  return (
    <div className="landing-container-new">
      <div className="landing-content">
        {/* Logo Section */}
        <div className="logo-section-new">
          <span className="lightning-icon-new">⚡</span>
          <h1 className="app-title-new">GigMarket</h1>
          <p className="app-subtitle-new">CONNECT. WORK. THRIVE.</p>
        </div>

        {/* Login Blocks */}
        <div className="login-blocks">
          {/* I Need Help - User Login */}
          <div 
            className="login-block user-block" 
            onClick={() => navigate('/dashboard/user')}
          >
            <div className="block-icon user-icon">
              <span>👤</span>
            </div>
            <h2 className="block-title">I need help</h2>
            <p className="block-subtitle">Find skilled workers near you</p>
            <div className="block-cta cyan-cta">
              Get Started <span className="arrow">→</span>
            </div>
            <div className="neon-underline cyan-underline"></div>
          </div>

          {/* I'm a Worker - Provider Login */}
          <div 
            className="login-block worker-block" 
            onClick={() => navigate('/dashboard/worker')}
          >
            <div className="block-icon worker-icon">
              <span>🛠️</span>
            </div>
            <h2 className="block-title">I'm a worker</h2>
            <p className="block-subtitle">Find gigs in your area</p>
            <div className="block-cta purple-cta">
              Start Earning <span className="arrow">→</span>
            </div>
            <div className="neon-underline purple-underline"></div>
          </div>
        </div>
      </div>
    </div>
  );
}

// Main App with Router
function App() {
  return (
    <Router>
      <div className="app">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/dashboard/user" element={<UserDashboard />} />
          <Route path="/dashboard/worker" element={<WorkerDashboard />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
