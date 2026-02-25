const router = require("express").Router();
const Booking = require("../models/Booking");
const auth = require("../middleware/auth");

// 📅 Create a new booking
router.post("/", auth, async (req, res) => {
    try {
        const { providerId, address, notes } = req.body;
        const booking = new Booking({
            userId: req.user.id,
            providerId,
            address,
            notes
        });
        await booking.save();
        res.status(201).json({ success: true, booking });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
});

// 📋 Get user bookings (as customer)
router.get("/", auth, async (req, res) => {
    try {
        const bookings = await Booking.find({ userId: req.user.id })
            .populate("providerId", "name category hourlyRate")
            .sort("-createdAt");
        res.json({ success: true, bookings });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

// 💼 Get assigned bookings (as provider)
router.get("/assigned", auth, async (req, res) => {
    try {
        // Note: This assumes the User ID is the same as the Provider ID for simplicity in this MVP
        // In a real app, we would search for the Provider linked to this User ID.
        const bookings = await Booking.find({ providerId: req.user.id })
            .populate("userId", "name phone")
            .sort("-createdAt");
        res.json({ success: true, bookings });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

// ❌ Cancel booking
router.patch("/:id/cancel", auth, async (req, res) => {
    try {
        const booking = await Booking.findOne({ _id: req.params.id, userId: req.user.id });
        if (!booking) return res.status(404).json({ message: "Booking not found" });

        booking.status = "Cancelled";
        await booking.save();
        res.json({ success: true, booking });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

module.exports = router;
