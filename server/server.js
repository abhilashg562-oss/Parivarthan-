const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
require("dotenv").config();

const app = express();
app.use(cors());
app.use(express.json());

// Only load providers route for now
app.use("/api/providers", require("./routes/providers"));

// Health check
app.get("/", (req, res) => {
  res.json({ status: "GigMarket API running 🚀" });
});

// Connect MongoDB
mongoose.connect(process.env.MONGO_URI)
  .then(() => {
    console.log("✅ MongoDB connected");
    app.listen(process.env.PORT || 5000, () => {
      console.log(`✅ Server running on port ${process.env.PORT || 5000}`);
    });
  })
  .catch((err) => console.error("❌ DB Error:", err));
