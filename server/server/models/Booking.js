const mongoose = require("mongoose");

const bookingSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    providerId: { type: mongoose.Schema.Types.ObjectId, ref: "Provider", required: true },
    status: {
        type: String,
        enum: ["Pending", "Accepted", "Completed", "Cancelled"],
        default: "Pending"
    },
    bookingDate: { type: Date, default: Date.now },
    address: String,
    notes: String,
}, { timestamps: true });

module.exports = mongoose.model("Booking", bookingSchema);
