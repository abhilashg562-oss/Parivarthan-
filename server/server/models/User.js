const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
  name: String,
  phone: { type: String, unique: true },
  password: String,
  role: { type: String, enum: ["customer", "provider"], default: "customer" },
}, { timestamps: true });

module.exports = mongoose.model("User", userSchema);
