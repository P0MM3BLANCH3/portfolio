const mongoose = require("mongoose");
const resources = require('../../resources.json');
const Schema = mongoose.Schema;

const Inventory = new Schema({
    userID: {
        type: String,
        required: true,
        unique: true
    },
    materials: [
        {
            name: {
                type: String,
                validate: {
                    validator: function(value) {
                        const materialsCategory = resources.products.find(m => m.name === "materials")
                        const materials = materialsCategory.find(m => m.name === value)
                        return materials ? true : false;
                    }
                }
            },
            stock: {
                type: Number,
                default: 0
            }
        }
    ]
});

module.exports = mongoose.model("inventory", Inventory);
