const { default: axios } = require("axios");
const cheerio = require("cheerio");
const express = require("express");
const router = express.Router();

router.get('/weather',async(req,res,next)=>{

    try{
        
        //안드로이드에서 쿼리스트링을 통해 페이지번호
        process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
        let kind = req.query.kind;
        console.log(encodeURI(kind))
        const response = await axios.get("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="+encodeURI(kind)+encodeURI("날씨"));
        let json ={
            air_temperature:null,
            rain_probability:null,
            humidity:null,
            wind_speed:null,
            weather:null
        };
    
        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);
            
            let air_temperature= $("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.weather_graphic > div.temperature_text > strong").text().trim();
            json.air_temperature = air_temperature.slice(5,air_temperature.length-1);
            json.rain_probability= $("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > dl > dd:nth-child(2)").text().trim();
            json.humidity= $("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > dl > dd:nth-child(4)").text().trim();
            json.wind_speed= $("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > dl > dt:nth-child(5)").text().trim()+" "+$("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > dl > dd:nth-child(6)").text().trim();
            json.weather = $("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > p > span:nth-child(2)").text().trim();
           
        }
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});


module.exports = router;