
import React, { useState, createContext, useContext } from 'react';
import './App.css';

// Language Context
const LanguageContext = createContext();

// Translations
const translations = {
  en: {
    // Navigation
    postGig: 'Post a Gig',
    activeGigs: 'Active Gigs',
    history: 'History',
    findGigs: 'Find Gigs',
    myGigs: 'My Gigs',
    earnings: 'Earnings',
    logout: 'Logout',

    // User Dashboard Headers
    postNewGig: 'Post a New Gig',
    yourActiveGigs: 'Your Active Gigs',
    gigHistory: 'Gig History',

    // Worker Dashboard Headers
    availableGigsNearYou: 'Available Gigs Near You',
    yourActiveGigsWorker: 'Your Active Gigs',
    yourEarnings: 'Your Earnings',

    // Common
    within10km: 'Within 10km',
    within5km: '5km radius',
    online: 'Online',
    customer: 'Customer',
    skilledWorker: 'Skilled Worker',

    // Form Labels
    whatDoYouNeed: 'What do you need?',
    selectServiceType: 'Select service type...',
    budget: 'Budget (₹)',
    enterAmount: 'Enter amount',
    describeWork: 'Describe the work needed',
    describePlaceholder: 'Describe what you need done... Be specific about the problem and any requirements.',
    preferredDate: 'Preferred Date',
    urgency: 'Urgency',
    flexible: 'Flexible',
    within24hours: 'Within 24 hours',
    emergency: 'Emergency (ASAP)',
    locationDetails: 'Location Details',
    enterAddress: 'Enter your address or landmark...',
    postGig: 'Post Gig',

    // Gig Details
    kitchenSinkRepair: 'Kitchen Sink Repair',
    fanInstallation: 'Fan Installation',
    bathroomLeakFix: 'Bathroom Leak Fix',
    pending: 'pending',
    accepted: 'accepted',
    completed: 'Completed',
    inProgress: 'In Progress',
    today: 'Today',
    tomorrow: 'Tomorrow',
    assigned: 'Assigned',
    viewDetails: 'View Details',
    cancel: 'Cancel',
    acceptGig: 'Accept Gig',
    details: 'Details',
    markComplete: 'Mark Complete',
    message: 'Message',

    // Earnings
    thisMonth: 'This Month',
    gigsCompleted: 'Gigs Completed',
    rating: 'Rating',
    earningsOverview: 'Earnings Overview',

    // Service Types
    plumbing: 'Plumbing',
    electrical: 'Electrical',
    carpentry: 'Carpentry',
    painting: 'Painting',
    cleaning: 'Cleaning',
    other: 'Other',

    // Worker Gigs
    pipeBurstEmergency: 'Pipe Burst Emergency',
    lightSwitchRepair: 'Light Switch Repair',
    doorHandleFix: 'Door Handle Fix',
    high: 'high',
    normal: 'normal',
    low: 'low',

    // Active Worker Gigs
    waterHeaterInstallation: 'Water Heater Installation',
    client: 'Client',
    away: 'away',
    workInProgress: 'Work in progress',
  },
  kn: {
    // Navigation
    postGig: 'ಗಿಗ್ ಅಪ್ಲೋಡ್ ಮಾಡಿ',
    activeGigs: 'ಸಕ್ರಿಯ ಗಿಗ್ಗಳು',
    history: 'ಇತಿಹಾಸ',
    findGigs: 'ಗಿಗ್ ಹುಡುಕಿ',
    myGigs: 'ನನ್ನ ಗಿಗ್ಗಳು',
    earnings: 'ಗಳಿಕೆ',
    logout: 'ಲಾಗ್‌ಔಟ್',

    // User Dashboard Headers
    postNewGig: 'ಹೊಸ ಗಿಗ್ ಅಪ್ಲೋಡ್ ಮಾಡಿ',
    yourActiveGigs: 'ನಿಮ್ಮ ಸಕ್ರಿಯ ಗಿಗ್ಗಳು',
    gigHistory: 'ಗಿಗ್ ಇತಿಹಾಸ',

    // Worker Dashboard Headers
    availableGigsNearYou: 'ನಿಮ್ಮ ಬಳಿ ಲಭ್ಯವಿರುವ ಗಿಗ್ಗಳು',
    yourActiveGigsWorker: 'ನಿಮ್ಮ ಸಕ್ರಿಯ ಗಿಗ್ಗಳು',
    yourEarnings: 'ನಿಮ್ಮ ಗಳಿಕೆ',

    // Common
    within10km: '10ಕಿ.ಮೀ ಒಳಗೆ',
    within5km: '5ಕಿ.ಮೀ ತ್ರಿಜ್ಯಾ',
    online: 'ಆನ್‌ಲೈನ್',
    customer: 'ಗ್ರಾಹಕ',
    skilledWorker: 'ಕುಶಲ ಕಾರ್ಮಿಕ',

    // Form Labels
    whatDoYouNeed: 'ನಿಮಗೆ ಬೇಕಾದ್ದು ಏನು?',
    selectServiceType: 'ಸೇವಾ ಪ್ರಕಾರವನ್ನು ಆರಿಸಿ...',
    budget: 'ಬಜೆಟ್ (₹)',
    enterAmount: 'ಮೊತ್ತವನ್ನು ನಮೂದಿಸಿ',
    describeWork: 'ಅಗತ್ಯವಿರುವ ಕೆಲಸವನ್ನು ವಿವರಿಸಿ',
    describePlaceholder: 'ಏನು ಮಾಡಬೇಕೆಂದು ವಿವರಿಸಿ... ಸಮಸ್ಯೆ ಮತ್ತು ಯಾವುದೇ ಅವಶ್ಯಕತೆಗಳ ಬಗ್ಗೆ ನಿರ್ದಿಷ್ಟವಾಗಿ ತಿಳಿಸಿ.',
    preferredDate: 'ಆದ್ಯತೆಯ ದಿನಾಂಕ',
    urgency: 'ತುರ್ತುತ್ವ',
    flexible: 'ಸಮಯ ಸ್ವಾತಂತ್ರ್ಯ',
    within24hours: '24 ಗಂಟೆಗಳ ಒಳಗೆ',
    emergency: 'ತುರ್ತು (ಬೇಗ)',
    locationDetails: 'ಸ್ಥಳ ವಿವರಗಳು',
    enterAddress: 'ನಿಮ್ಮ ವಿಳಾಸ ಅಥವಾ ಲ್ಯಾಂಡ್‌ಮಾರ್ಕ್ ನಮೂದಿಸಿ...',
    postGig: 'ಗಿಗ್ ಅಪ್ಲೋಡ್',

    // Gig Details
    kitchenSinkRepair: 'ಕಿಚನ್ ಸಿಂಕ್ ರಿಪೇರಿ',
    fanInstallation: 'ಫ್ಯಾನ್ ಸ್ಥಾಪನೆ',
    bathroomLeakFix: 'ಬಾತ್‌ರೂಮ್ ಲೀಕ್ ಫಿಕ್ಸ್',
    pending: 'ತೇರ್ಗಡೆಯಾಗಿದೆ',
    accepted: 'ಸ್ವೀಕರಿಸಲಾಗಿದೆ',
    completed: 'ಪೂರ್ಣಗೊಂಡಿದೆ',
    inProgress: 'ಪ್ರಗತಿಯಲ್ಲಿ',
    today: 'ಇಂದು',
    tomorrow: 'ನಾಳೆ',
    assigned: 'ನಿಯೋಜಿಸಲಾಗಿದೆ',
    viewDetails: 'ವಿವರಗಳನ್ನು ನೋಡಿ',
    cancel: 'ರದ್ದುಮಾಡಿ',
    acceptGig: 'ಗಿಗ್ ಸ್ವೀಕರಿಸಿ',
    details: 'ವಿವರಗಳು',
    markComplete: 'ಪೂರ್ಣಗೊಂಡಿದೆ ಎಂದು ಗುರುತಿಸಿ',
    message: 'ಸಂದೇಶ',

    // Earnings
    thisMonth: 'ಈ ತಿಂಗಳು',
    gigsCompleted: 'ಗಿಗ್‌ಗಳು ಪೂರ್ಣಗೊಂಡವು',
    rating: 'ರೇಟಿಂಗ್',
    earningsOverview: 'ಗಳಿಕೆ ಅವಲೋಕನ',

    // Service Types
    plumbing: 'ಪ್ಲಂಬಿಂಗ್',
    electrical: 'ಎಲೆಕ್ಟ್ರಿಕಲ್',
    carpentry: 'ಕಾರ್ಪೆಂಟರಿ',
    painting: 'ಪೇಂಟಿಂಗ್',
    cleaning: 'ಕ್ಲೀನಿಂಗ್',
    other: 'ಇತರೆ',

    // Worker Gigs
    pipeBurstEmergency: 'ಪೈಪ್ ಬರ್ಸ್ಟ್ ತುರ್ತು',
    lightSwitchRepair: 'ಲೈಟ್ ಸ್ವಿಚ್ ರಿಪೇರಿ',
    doorHandleFix: 'ಬಾಗಿಲು ಹ್ಯಾಂಡಲ್ ಫಿಕ್ಸ್',
    high: 'ಎತ್ತರ',
    normal: 'ಸಾಮಾನ್ಯ',
    low: 'ಕಡಿಮೆ',

    // Active Worker Gigs
    waterHeaterInstallation: 'ವಾಟರ್ ಹೀಟರ್ ಸ್ಥಾಪನೆ',
    client: 'ಗ್ರಾಹಕ',
    away: 'ದೂರ',
    workInProgress: 'ಕೆಲಸ ಪ್ರಗತಿಯಲ್ಲಿ',
  },
  hi: {
    // Navigation
    postGig: 'गिग पोस्ट करें',
    activeGigs: 'सक्रिय गिग्स',
    history: 'इतिहास',
    findGigs: 'गिग खोजें',
    myGigs: 'मेरी गिग्स',
    earnings: 'कमाई',
    logout: 'लॉगआउट',

    // User Dashboard Headers
    postNewGig: 'नई गिग पोस्ट करें',
    yourActiveGigs: 'आपकी सक्रिय गिग्स',
    gigHistory: 'गिग इतिहास',

    // Worker Dashboard Headers
    availableGigsNearYou: 'आपके पास उपलब्ध गिग्स',
    yourActiveGigsWorker: 'आपकी सक्रिय गिग्स',
    yourEarnings: 'आपकी कमाई',

    // Common
    within10km: '10कि.मी. के भीतर',
    within5km: '5कि.मी. त्रिज्या',
    online: 'ऑनलाइन',
    customer: 'ग्राहक',
    skilledWorker: 'कुशल कार्यकर्ता',

    // Form Labels
    whatDoYouNeed: 'आपको क्या चाहिए?',
    selectServiceType: 'सेवा प्रकार चुनें...',
    budget: 'बजट (₹)',
    enterAmount: 'राशि दर्ज करें',
    describeWork: 'आवश्यक कार्य का वर्णन करें',
    describePlaceholder: 'जो काम करवाना है उसका वर्णन करें... समस्या और किसी भी आवश्यकताओं के बारे में विशिष्ट रूप से बताएं।',
    preferredDate: 'पसंदीदा तारीख',
    urgency: 'अत्यावश्यकता',
    flexible: 'लचीला',
    within24hours: '24 घंटों के भीतर',
    emergency: 'आपातकाल (जितनी जल्दी हो सके)',
    locationDetails: 'स्थान विवरण',
    enterAddress: 'अपना पता या लैंडमार्क दर्ज करें...',
    postGig: 'गिग पोस्ट करें',

    // Gig Details
    kitchenSinkRepair: 'किचन सिंक मरम्मत',
    fanInstallation: 'पंखे की स्थापना',
    bathroomLeakFix: 'बाथरूम रिसाव ठीक करें',
    pending: 'लंबित',
    accepted: 'स्वीकृत',
    completed: 'पूर्ण',
    inProgress: 'प्रगति में',
    today: 'आज',
    tomorrow: 'कल',
    assigned: 'सौंपा गया',
    viewDetails: 'विवरण देखें',
    cancel: 'रद्द करें',
    acceptGig: 'गिग स्वीकार करें',
    details: 'विवरण',
    markComplete: 'पूर्ण चिह्नित करें',
    message: 'संदेश',

    // Earnings
    thisMonth: 'इस महीने',
    gigsCompleted: 'पूर्ण गिग्स',
    rating: 'रेटिंग',
    earningsOverview: 'कमाई अवलोकन',

    // Service Types
    plumbing: 'प्लंबिंग',
    electrical: 'इलेक्ट्रिकल',
    carpentry: 'कारपेंटरी',
    painting: 'पेंटिंग',
    cleaning: 'सफाई',
    other: 'अन्य',

    // Worker Gigs
    pipeBurstEmergency: 'पाइप बर्स्ट आपातकाल',
    lightSwitchRepair: 'लाइट स्विच मरम्मत',
    doorHandleFix: 'दरवाजे का हैंडल ठीक करें',
    high: 'उच्च',
    normal: 'सामान्य',
    low: 'कम',

    // Active Worker Gigs
    waterHeaterInstallation: 'वाटर हीटर स्थापना',
    client: 'ग्राहक',
    away: 'दूर',
    workInProgress: 'काम प्रगति पर है',
  }
};

// Language Selector Component
function LanguageSelector() {
  const { language, setLanguage } = useContext(LanguageContext);

  return (
    <div className="language-selector">
      <select
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
        className="language-dropdown"
      >
        <option value="en">English</option>
        <option value="kn">ಕನ್ನಡ</option>
        <option value="hi">हिंदी</option>
      </select>
    </div>
  );
}

// Translation hook
function useTranslation() {
  const { language } = useContext(LanguageContext);
  return (key) => translations[language][key] || translations['en'][key] || key;
}

// Main App Component
function App() {
  const [currentScreen, setCurrentScreen] = useState('landing'); // landing, userLogin, workerLogin, userOTP, workerOTP, userDashboard, workerDashboard
  const [userData, setUserData] = useState(null);
  const [workerData, setWorkerData] = useState(null);
  const [language, setLanguage] = useState('en');
  const [tempUserData, setTempUserData] = useState(null);
  const [tempWorkerData, setTempWorkerData] = useState(null);

  const navigateTo = (screen) => {
    setCurrentScreen(screen);
  };

  const handleUserLogin = (data) => {
    setTempUserData(data);
    navigateTo('userOTP');
  };

  const handleWorkerLogin = (data) => {
    setTempWorkerData(data);
    navigateTo('workerOTP');
  };

  const handleUserOTPVerified = () => {
    setUserData(tempUserData);
    setTempUserData(null);
    navigateTo('userDashboard');
  };

  const handleWorkerOTPVerified = () => {
    setWorkerData(tempWorkerData);
    setTempWorkerData(null);
    navigateTo('workerDashboard');
  };

  const handleLogout = () => {
    setUserData(null);
    setWorkerData(null);
    navigateTo('landing');
  };

  return (
    <LanguageContext.Provider value={{ language, setLanguage }}>
      <div className="app">
        {currentScreen === 'landing' && <LandingPage onNavigate={navigateTo} />}
        {currentScreen === 'userLogin' && <UserLogin onNavigate={navigateTo} onLogin={handleUserLogin} />}
        {currentScreen === 'workerLogin' && <WorkerLogin onNavigate={navigateTo} onLogin={handleWorkerLogin} />}
        {currentScreen === 'userOTP' && <UserOTP onNavigate={navigateTo} onVerify={handleUserOTPVerified} phone={tempUserData?.phone} />}
        {currentScreen === 'workerOTP' && <WorkerOTP onNavigate={navigateTo} onVerify={handleWorkerOTPVerified} phone={tempWorkerData?.phone} />}
        {currentScreen === 'userDashboard' && <UserDashboard userData={userData} onLogout={handleLogout} />}
        {currentScreen === 'workerDashboard' && <WorkerDashboard workerData={workerData} onLogout={handleLogout} />}
        {currentScreen === 'emptyDashboard' && <EmptyDashboard onBack={() => navigateTo('landing')} />}
      </div>
    </LanguageContext.Provider>
  );
}

// Landing Page Component
function LandingPage({ onNavigate }) {
  return (
    <div className="landing-container">
      <div className="content-wrapper">
        <div className="logo-section">
          <div className="logo-icon-container">
            <span className="lightning-icon">⚡</span>
          </div>
          <h1 className="app-title-gradient">GigMarket</h1>
          <p className="app-subtitle">CONNECT. WORK. THRIVE.</p>
        </div>

        <div className="role-cards-container">
          <div className="role-card-new help-card" onClick={() => onNavigate('emptyDashboard')}>
            <div className="card-icon-new user-icon">👤</div>
            <h2 className="card-title-new">I need help</h2>
            <p className="card-desc-new">Find skilled workers near you</p>
            <div className="card-divider cyan-line"></div>
            <span className="card-link-new cyan-text">Get Started →</span>
          </div>

          <div className="role-card-new worker-card-new" onClick={() => onNavigate('emptyDashboard')}>
            <div className="card-icon-new worker-icon">🛠️</div>
            <h2 className="card-title-new">I'm a worker</h2>
            <p className="card-desc-new">Find gigs in your area</p>
            <div className="card-divider purple-line"></div>
            <span className="card-link-new purple-text">Start Earning →</span>
          </div>
        </div>
      </div>
    </div>
  );
}

// User Login Component
function UserLogin({ onNavigate, onLogin }) {
  const [formData, setFormData] = useState({ name: '', phone: '' });
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true);
    // Simulate API call
    setTimeout(() => {
      onLogin(formData);
      setIsLoading(false);
    }, 1000);
  };

  return (
    <div className="auth-container">
      <div className="gradient-bg"></div>
      <div className="auth-card">
        <button className="back-btn" onClick={() => onNavigate('landing')}>← Back</button>

        <div className="auth-header">
          <div className="auth-icon">👤</div>
          <h2>User Login</h2>
          <p>Find skilled workers near you</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="input-group">
            <label>Full Name</label>
            <input
              type="text"
              placeholder="Enter your name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              required
            />
            <div className="input-glow"></div>
          </div>

          <div className="input-group">
            <label>Phone Number</label>
            <input
              type="tel"
              placeholder="+91 98765 43210"
              value={formData.phone}
              onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
              required
            />
            <div className="input-glow"></div>
          </div>

          <button type="submit" className="submit-btn" disabled={isLoading}>
            {isLoading ? (
              <span className="loading-spinner"></span>
            ) : (
              'Continue →'
            )}
          </button>
        </form>

        <div className="auth-footer">
          <p>Are you a worker? <span onClick={() => onNavigate('workerLogin')} className="switch-link">Join as Worker</span></p>
        </div>
      </div>
    </div>
  );
}

// Worker Login Component
function WorkerLogin({ onNavigate, onLogin }) {
  const [formData, setFormData] = useState({
    name: '',
    phone: '',
    trade: '',
    experience: ''
  });
  const [isLoading, setIsLoading] = useState(false);

  const trades = ['Plumber', 'Electrician', 'Carpenter', 'Painter', 'Mechanic', 'Cleaner', 'Driver', 'Other'];

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true);
    setTimeout(() => {
      onLogin(formData);
      setIsLoading(false);
    }, 1000);
  };

  return (
    <div className="auth-container">
      <div className="gradient-bg"></div>
      <div className="auth-card worker">
        <button className="back-btn" onClick={() => onNavigate('landing')}>← Back</button>

        <div className="auth-header">
          <div className="auth-icon purple">🛠️</div>
          <h2>Worker Login</h2>
          <p>Start finding gigs in your area</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="input-group">
            <label>Full Name</label>
            <input
              type="text"
              placeholder="Enter your name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              required
            />
            <div className="input-glow purple"></div>
          </div>

          <div className="input-group">
            <label>Phone Number</label>
            <input
              type="tel"
              placeholder="+91 98765 43210"
              value={formData.phone}
              onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
              required
            />
            <div className="input-glow purple"></div>
          </div>

          <div className="input-row">
            <div className="input-group half">
              <label>Trade/Skill</label>
              <select
                value={formData.trade}
                onChange={(e) => setFormData({ ...formData, trade: e.target.value })}
                required
              >
                <option value="">Select</option>
                {trades.map(trade => (
                  <option key={trade} value={trade}>{trade}</option>
                ))}
              </select>
              <div className="input-glow purple"></div>
            </div>

            <div className="input-group half">
              <label>Experience</label>
              <select
                value={formData.experience}
                onChange={(e) => setFormData({ ...formData, experience: e.target.value })}
                required
              >
                <option value="">Select</option>
                <option value="0-1">0-1 years</option>
                <option value="1-3">1-3 years</option>
                <option value="3-5">3-5 years</option>
                <option value="5+">5+ years</option>
              </select>
              <div className="input-glow purple"></div>
            </div>
          </div>

          <button type="submit" className="submit-btn purple" disabled={isLoading}>
            {isLoading ? (
              <span className="loading-spinner"></span>
            ) : (
              'Start Earning →'
            )}
          </button>
        </form>

        <div className="auth-footer">
          <p>Need to hire? <span onClick={() => onNavigate('userLogin')} className="switch-link">Join as User</span></p>
        </div>
      </div>
    </div>
  );
}

// User Dashboard Component
function UserDashboard({ userData, onLogout }) {
  const [activeTab, setActiveTab] = useState('post'); // post, active, history
  const t = useTranslation();

  return (
    <div className="dashboard-centered">
      <div className="dashboard-card">
        <div className="dashboard-card-header">
          <div>
            <h1>
              {activeTab === 'post' && t('postNewGig')}
              {activeTab === 'active' && t('yourActiveGigs')}
              {activeTab === 'history' && t('gigHistory')}
            </h1>
            <p style={{ color: 'var(--text-secondary)', marginTop: '0.5rem' }}>📍 {t('within10km')}</p>
          </div>
          <div className="header-actions">
            <LanguageSelector />
            <div className="user-info" style={{ marginBottom: 0 }}>
              <div className="user-avatar">{userData?.name?.[0] || 'U'}</div>
              <div className="user-details">
                <p className="user-name">{userData?.name || 'User'}</p>
              </div>
            </div>
            <button className="logout-btn" onClick={onLogout} style={{ width: 'auto', padding: '0.5rem 1rem' }}>{t('logout')}</button>
          </div>
        </div>

        <div className="dashboard-card-content">
          <div className="dashboard-card-sidebar">
            <nav className="sidebar-nav" style={{ gap: '0.5rem' }}>
              <button
                className={`nav-item ${activeTab === 'post' ? 'active' : ''}`}
                onClick={() => setActiveTab('post')}
              >
                <span>➕</span> {t('postGig')}
              </button>
              <button
                className={`nav-item ${activeTab === 'active' ? 'active' : ''}`}
                onClick={() => setActiveTab('active')}
              >
                <span>📋</span> {t('activeGigs')}
              </button>
              <button
                className={`nav-item ${activeTab === 'history' ? 'active' : ''}`}
                onClick={() => setActiveTab('history')}
              >
                <span>📜</span> {t('history')}
              </button>
            </nav>
          </div>

          <div className="dashboard-card-main">
            {activeTab === 'post' && <PostGigForm t={t} />}
            {activeTab === 'active' && <ActiveGigs t={t} />}
            {activeTab === 'history' && <GigHistory t={t} />}
          </div>
        </div>
      </div>
    </div>
  );
}

// Worker Dashboard Component
function WorkerDashboard({ workerData, onLogout }) {
  const [activeTab, setActiveTab] = useState('available'); // available, active, earnings
  const t = useTranslation();

  return (
    <div className="dashboard-centered">
      <div className="dashboard-card worker">
        <div className="dashboard-card-header">
          <div>
            <h1>
              {activeTab === 'available' && t('availableGigsNearYou')}
              {activeTab === 'active' && t('yourActiveGigsWorker')}
              {activeTab === 'earnings' && t('yourEarnings')}
            </h1>
            <p style={{ color: 'var(--text-secondary)', marginTop: '0.5rem' }}>📍 {t('within5km')} • <span style={{ color: '#2ed573' }}>● {t('online')}</span></p>
          </div>
          <div className="header-actions">
            <LanguageSelector />
            <div className="user-info" style={{ marginBottom: 0 }}>
              <div className="user-avatar purple">{workerData?.name?.[0] || 'W'}</div>
              <div className="user-details">
                <p className="user-name">{workerData?.name || 'Worker'}</p>
              </div>
            </div>
            <button className="logout-btn" onClick={onLogout} style={{ width: 'auto', padding: '0.5rem 1rem' }}>{t('logout')}</button>
          </div>
        </div>

        <div className="dashboard-card-content">
          <div className="dashboard-card-sidebar">
            <nav className="sidebar-nav" style={{ gap: '0.5rem' }}>
              <button
                className={`nav-item ${activeTab === 'available' ? 'active' : ''}`}
                onClick={() => setActiveTab('available')}
              >
                <span>🔍</span> {t('findGigs')}
              </button>
              <button
                className={`nav-item ${activeTab === 'active' ? 'active' : ''}`}
                onClick={() => setActiveTab('active')}
              >
                <span>⚡</span> {t('myGigs')}
              </button>
              <button
                className={`nav-item ${activeTab === 'earnings' ? 'active' : ''}`}
                onClick={() => setActiveTab('earnings')}
              >
                <span>💰</span> {t('earnings')}
              </button>
            </nav>
          </div>

          <div className="dashboard-card-main">
            {activeTab === 'available' && <AvailableGigs t={t} />}
            {activeTab === 'active' && <WorkerActiveGigs t={t} />}
            {activeTab === 'earnings' && <Earnings t={t} />}
          </div>
        </div>
      </div>
    </div>
  );
}

// Sub-components for User Dashboard
function PostGigForm({ t }) {
  return (
    <div className="form-card">
      <div className="form-section">
        <h3>Gig Details</h3>
        <div className="form-grid">
          <div className="input-group">
            <label>{t('whatDoYouNeed')}</label>
            <select className="dashboard-select">
              <option>{t('selectServiceType')}</option>
              <option>{t('plumbing')}</option>
              <option>{t('electrical')}</option>
              <option>{t('carpentry')}</option>
              <option>{t('painting')}</option>
              <option>{t('cleaning')}</option>
              <option>{t('other')}</option>
            </select>
          </div>

          <div className="input-group">
            <label>{t('budget')}</label>
            <input type="number" placeholder={t('enterAmount')} className="dashboard-input" />
          </div>
        </div>

        <div className="input-group">
          <label>{t('describeWork')}</label>
          <textarea
            placeholder={t('describePlaceholder')}
            className="dashboard-textarea"
            rows="4"
          ></textarea>
        </div>

        <div className="form-grid">
          <div className="input-group">
            <label>{t('preferredDate')}</label>
            <input type="date" className="dashboard-input" />
          </div>

          <div className="input-group">
            <label>{t('urgency')}</label>
            <select className="dashboard-select">
              <option>{t('flexible')}</option>
              <option>{t('within24hours')}</option>
              <option>{t('emergency')}</option>
            </select>
          </div>
        </div>

        <div className="input-group">
          <label>{t('locationDetails')}</label>
          <input
            type="text"
            placeholder={t('enterAddress')}
            className="dashboard-input"
          />
        </div>

        <button className="action-btn primary">
          <span>🚀</span> {t('postGig')}
        </button>
      </div>
    </div>
  );
}

function ActiveGigs({ t }) {
  const gigs = [
    { id: 1, title: t('kitchenSinkRepair'), status: 'pending', budget: '₹500', date: `${t('today')}, 2:00 PM`, worker: null },
    { id: 2, title: t('fanInstallation'), status: 'accepted', budget: '₹800', date: `${t('tomorrow')}, 10:00 AM`, worker: 'Ramesh K.' }
  ];

  return (
    <div className="gigs-list">
      {gigs.map(gig => (
        <div key={gig.id} className="gig-card">
          <div className="gig-header">
            <h4>{gig.title}</h4>
            <span className={`status-tag ${gig.status}`}>{t(gig.status)}</span>
          </div>
          <div className="gig-details">
            <p><span>💰</span> {gig.budget}</p>
            <p><span>📅</span> {gig.date}</p>
            {gig.worker && <p><span>👤</span> {t('assigned')}: {gig.worker}</p>}
          </div>
          <div className="gig-actions">
            <button className="action-btn small">{t('viewDetails')}</button>
            {gig.status === 'pending' && <button className="action-btn small danger">{t('cancel')}</button>}
          </div>
        </div>
      ))}
    </div>
  );
}

function GigHistory({ t }) {
  return (
    <div className="gigs-list">
      <div className="gig-card completed">
        <div className="gig-header">
          <h4>{t('bathroomLeakFix')}</h4>
          <span className="status-tag completed">{t('completed')}</span>
        </div>
        <div className="gig-details">
          <p><span>💰</span> ₹1,200</p>
          <p><span>📅</span> 2 {t('history')}</p>
          <p><span>⭐</span> Rated 5/5</p>
        </div>
      </div>
    </div>
  );
}

// Sub-components for Worker Dashboard
function AvailableGigs({ t }) {
  const gigs = [
    { id: 1, title: t('pipeBurstEmergency'), distance: '2.3km', budget: '₹1,500', urgency: 'high', category: t('plumbing') },
    { id: 2, title: t('lightSwitchRepair'), distance: '1.8km', budget: '₹400', urgency: 'normal', category: t('electrical') },
    { id: 3, title: t('doorHandleFix'), distance: '4.1km', budget: '₹600', urgency: 'low', category: t('carpentry') }
  ];

  return (
    <div className="gigs-grid">
      {gigs.map(gig => (
        <div key={gig.id} className="gig-card available">
          <div className="gig-category">{gig.category}</div>
          <h4>{gig.title}</h4>
          <div className="gig-meta">
            <span className="distance">📍 {gig.distance}</span>
            <span className={`urgency ${gig.urgency}`}>{t(gig.urgency)}</span>
          </div>
          <div className="gig-budget">{gig.budget}</div>
          <div className="gig-actions">
            <button className="action-btn primary full">{t('acceptGig')}</button>
            <button className="action-btn secondary full">{t('details')}</button>
          </div>
        </div>
      ))}
    </div>
  );
}

function WorkerActiveGigs({ t }) {
  return (
    <div className="gigs-list">
      <div className="gig-card active">
        <div className="gig-header">
          <h4>{t('waterHeaterInstallation')}</h4>
          <span className="status-tag in-progress">{t('inProgress')}</span>
        </div>
        <div className="gig-details">
          <p><span>👤</span> {t('client')}: Suresh P.</p>
          <p><span>📍</span> 3.2km {t('away')}</p>
          <p><span>💰</span> ₹2,500</p>
        </div>
        <div className="gig-progress">
          <div className="progress-bar">
            <div className="progress-fill" style={{ width: '60%' }}></div>
          </div>
          <span>{t('workInProgress')}</span>
        </div>
        <div className="gig-actions">
          <button className="action-btn primary">{t('markComplete')}</button>
          <button className="action-btn small">{t('message')}</button>
        </div>
      </div>
    </div>
  );
}

function Earnings({ t }) {
  return (
    <div className="earnings-container">
      <div className="stats-grid">
        <div className="stat-card">
          <h3>₹12,500</h3>
          <p>{t('thisMonth')}</p>
          <div className="stat-trend up">+15%</div>
        </div>
        <div className="stat-card">
          <h3>24</h3>
          <p>{t('gigsCompleted')}</p>
          <div className="stat-trend up">+8</div>
        </div>
        <div className="stat-card">
          <h3>4.8</h3>
          <p>{t('rating')}</p>
          <div className="stat-trend">⭐⭐⭐⭐⭐</div>
        </div>
      </div>

      <div className="earnings-chart-placeholder">
        <h4>{t('earningsOverview')}</h4>
        <div className="chart-mock">
          <div className="chart-bar" style={{ height: '40%' }}></div>
          <div className="chart-bar" style={{ height: '65%' }}></div>
          <div className="chart-bar" style={{ height: '45%' }}></div>
          <div className="chart-bar" style={{ height: '80%' }}></div>
          <div className="chart-bar active" style={{ height: '60%' }}></div>
        </div>
      </div>
    </div>
  );
}

// User OTP Verification Component
function UserOTP({ onNavigate, onVerify, phone }) {
  const [otp, setOtp] = useState(['', '', '', '']);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleOtpChange = (index, value) => {
    if (isNaN(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value;
    setOtp(newOtp);
    setError('');

    // Auto-focus next input
    if (value && index < 3) {
      const nextInput = document.getElementById(`user-otp-${index + 1}`);
      if (nextInput) nextInput.focus();
    }
  };

  const handleKeyDown = (index, e) => {
    if (e.key === 'Backspace' && !otp[index] && index > 0) {
      const prevInput = document.getElementById(`user-otp-${index - 1}`);
      if (prevInput) {
        prevInput.focus();
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const otpValue = otp.join('');
    if (otpValue.length !== 4) {
      setError('Please enter a valid 4-digit OTP');
      return;
    }
    setIsLoading(true);
    // Demo OTP - any 4 digits will work
    setTimeout(() => {
      onVerify();
      setIsLoading(false);
    }, 1000);
  };

  const handleResend = () => {
    // Demo resend
    alert('OTP resent to ' + phone);
  };

  return (
    <div className="auth-container">
      <div className="gradient-bg"></div>
      <div className="auth-card centered">
        <button className="back-btn" onClick={() => onNavigate('userLogin')}>← Back</button>

        <div className="auth-header">
          <div className="auth-icon">🔐</div>
          <h2>Verify OTP</h2>
          <p>Enter the 4-digit code sent to {phone}</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="otp-input-group">
            {otp.map((digit, index) => (
              <input
                key={index}
                id={`user-otp-${index}`}
                type="text"
                maxLength="1"
                value={digit}
                onChange={(e) => handleOtpChange(index, e.target.value)}
                onKeyDown={(e) => handleKeyDown(index, e)}
                className="otp-input"
                inputMode="numeric"
              />
            ))}
          </div>

          {error && <p className="error-message">{error}</p>}

          <p className="otp-hint">Demo: Enter any 4-digit number</p>

          <button type="submit" className="submit-btn" disabled={isLoading}>
            {isLoading ? (
              <span className="loading-spinner"></span>
            ) : (
              'Verify & Login'
            )}
          </button>
        </form>

        <div className="auth-footer">
          <p>Didn't receive code? <span onClick={handleResend} className="switch-link">Resend OTP</span></p>
        </div>
      </div>
    </div>
  );
}

// Worker OTP Verification Component
function WorkerOTP({ onNavigate, onVerify, phone }) {
  const [otp, setOtp] = useState(['', '', '', '']);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleOtpChange = (index, value) => {
    if (isNaN(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value;
    setOtp(newOtp);
    setError('');

    // Auto-focus next input
    if (value && index < 3) {
      const nextInput = document.getElementById(`worker-otp-${index + 1}`);
      if (nextInput) nextInput.focus();
    }
  };

  const handleKeyDown = (index, e) => {
    if (e.key === 'Backspace' && !otp[index] && index > 0) {
      const prevInput = document.getElementById(`worker-otp-${index - 1}`);
      if (prevInput) {
        prevInput.focus();
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const otpValue = otp.join('');
    if (otpValue.length !== 4) {
      setError('Please enter a valid 4-digit OTP');
      return;
    }
    setIsLoading(true);
    // Demo OTP - any 4 digits will work
    setTimeout(() => {
      onVerify();
      setIsLoading(false);
    }, 1000);
  };

  const handleResend = () => {
    // Demo resend
    alert('OTP resent to ' + phone);
  };

  return (
    <div className="auth-container">
      <div className="gradient-bg"></div>
      <div className="auth-card worker centered">
        <button className="back-btn" onClick={() => onNavigate('workerLogin')}>← Back</button>

        <div className="auth-header">
          <div className="auth-icon purple">🔐</div>
          <h2>Verify OTP</h2>
          <p>Enter the 4-digit code sent to {phone}</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="otp-input-group">
            {otp.map((digit, index) => (
              <input
                key={index}
                id={`worker-otp-${index}`}
                type="text"
                maxLength="1"
                value={digit}
                onChange={(e) => handleOtpChange(index, e.target.value)}
                onKeyDown={(e) => handleKeyDown(index, e)}
                className="otp-input"
                inputMode="numeric"
              />
            ))}
          </div>

          {error && <p className="error-message">{error}</p>}

          <p className="otp-hint">Demo: Enter any 4-digit number</p>

          <button type="submit" className="submit-btn purple" disabled={isLoading}>
            {isLoading ? (
              <span className="loading-spinner"></span>
            ) : (
              'Verify & Login'
            )}
          </button>
        </form>

        <div className="auth-footer">
          <p>Didn't receive code? <span onClick={handleResend} className="switch-link">Resend OTP</span></p>
        </div>
      </div>
    </div>
  );
}

// Empty Dashboard Component
function EmptyDashboard({ onBack }) {
  return (
    <div className="empty-dashboard-container">
      <button className="back-btn-top" onClick={onBack}>←</button>
      <div className="empty-dashboard-content">
        <div className="empty-dashboard-icon">🏠</div>
        <h1>Dashboard</h1>
        <p>No activities yet.</p>
        <p className="empty-sub">Your notifications and recent gigs will appear here.</p>
      </div>
    </div>
  );
}

export default App;
