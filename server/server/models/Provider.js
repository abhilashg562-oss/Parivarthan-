const mongoose = require("mongoose");

const providerSchema = new mongoose.Schema({
  name: { type: String, required: true },
  phone: { type: String, required: true, unique: true },
  skills: [String],                        // ["Plumber", "Electrician"]
  category: String,                        // "Repairs"
  hourlyRate: Number,
  bio: String,
  photo: String,
  languages: [String],                     // ["Kannada", "Hindi"]
  rating: { type: Number, default: 0 },
  totalReviews: { type: Number, default: 0 },
  isVerified: { type: Boolean, default: false },
  isAvailable: { type: Boolean, default: true },

  // 🗺️ GeoJSON for 5km proximity search
  location: {
    type: { type: String, enum: ["Point"], default: "Point" },
    coordinates: [Number],               // [longitude, latitude]
    address: String,
  },
}, { timestamps: true });

// Required for $nearSphere queries
providerSchema.index({ location: "2dsphere" });

module.exports = mongoose.model("Provider", providerSchema);
