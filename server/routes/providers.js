const router = require("express").Router();
const Provider = require("../models/Provider");

// 🔍 Search providers within 5km radius
// GET /api/providers/nearby?lat=12.97&lng=76.58&radius=5000&category=Repairs
router.get("/nearby", async (req, res) => {
  try {
    const { lat, lng, radius = 5000, category } = req.query;
    const query = {
      location: {
        $nearSphere: {
          $geometry: { type: "Point", coordinates: [parseFloat(lng), parseFloat(lat)] },
          $maxDistance: parseInt(radius),  // in meters
        }
      },
      isAvailable: true,
    };
    if (category) query.category = category;

    const providers = await Provider.find(query).limit(20);
    res.json({ success: true, count: providers.length, providers });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
});

// GET single provider
router.get("/:id", async (req, res) => {
  try {
    const provider = await Provider.findById(req.params.id);
    if (!provider) return res.status(404).json({ message: "Not found" });
    res.json({ success: true, provider });
  } catch (err) {
    res.status(500).json({ success: false, message: err.message });
  }
});

// POST register new provider
router.post("/register", async (req, res) => {
  try {
    const provider = new Provider(req.body);
    await provider.save();
    res.status(201).json({ success: true, provider });
  } catch (err) {
    res.status(400).json({ success: false, message: err.message });
  }
});

module.exports = router;
