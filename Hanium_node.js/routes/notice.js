const { default: axios } = require("axios");
const cheerio = require("cheerio");
const express = require("express");
const router = express.Router();

router.get('/ulsan',async(req,res,next)=>{

    try{
         process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
         const response = await axios.get("https://www.upa.or.kr/bbs/list.do?bbsId=BBS_0000000000000059&mId=001003010000000000");
        
        let json ={
            kind: "울산",
            title:null,
            url:null,
            date: null
        };
        let titles = [];
        let urls = [];
        let dates = [];

        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);
            
          
            for(let i=1; i<=5; i++){
                $(`#listForm > table > tbody > tr:nth-child(${i}) > td.dleft > a`).each(function(item,index,array){
                    titles.push($(this).text().trim());
                    urls.push("https://www.upa.or.kr/bbs"+$(this).attr("href").slice(1));
                 });
            }
            for(let i=1; i<=5; i++){
                $(`#listForm > table > tbody > tr:nth-child(${i}) > td:nth-child(4)`).each(function(item,index,array){
                    dates.push($(this).text().trim());
                 });
            }
        }

        json.title = titles;
        json.url = urls;
        json.date = dates;
    
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});

router.get('/busan',async(req,res,next)=>{

    try{
         process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
         const response = await axios.get("https://www.busanpa.com/kor/Board.do?mCode=MN1439");
        
        let json ={
            kind: "부산",
            title:null,
            url:null,
            date:null
        };
        let titles = [];
        let urls = [];
        let dates = [];

        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);
            
          
         
            for(let i=11; i<20; i+=2){
                $(`#board-wrap > table > tbody > tr:nth-child(${i}) > td.subject > a`).each(function(item,index,array){
                    titles.push($(this).text().trim());
                    urls.push("https://www.busanpa.com/kor/Board.do?mode=view&mCode=MN1439&idx="+$(this).attr("onclick").slice(6,11));
                 });
            }

           
            for(let i=11; i<20; i+=2){
                $(` #board-wrap > table > tbody > tr:nth-child(${i}) > td.date`).each(function(item,index,array){
                    dates.push($(this).text().trim());
                 });
            }
        }

        json.title = titles;
        json.url = urls;
        json.date = dates;
       
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});

router.get('/incheon',async(req,res,next)=>{

    try{
         process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
         const response = await axios.get("https://www.icpa.or.kr/article/list.do?boardKey=213&menuKey=397&currentPageNo=1");
        
        let json ={
            kind: "인천",
            title:null,
            url:null,
            date:null
        };
        let titles = [];
        let urls = [];
        let dates = [];

        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);
           
            for(let i=2; i<=6; i++){
                $(`#searchForm > div.board_body > table > tbody > tr:nth-child(${i}) > td.tx_al > a`).each(function(item,index,array){
                    titles.push($(this).text().trim());
                    urls.push("https://www.icpa.or.kr/article/"+$(this).attr("href"));
                 });
            }

            for(let i=2; i<=6; i++){
                $(` #searchForm > div.board_body > table > tbody > tr:nth-child(${i}) > td:nth-child(3)`).each(function(item,index,array){
                    dates.push($(this).text().trim());
                 });
            }
        }

        json.title = titles;
        json.url = urls;
        json.date = dates;
       
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});

router.get('/yeosu',async(req,res,next)=>{

    try{
         process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
         const response = await axios.get("https://www.ygpa.or.kr/kr/ygpa/notice");
        
        let json ={
            kind: "여수",
            title:null,
            url:null,
            date:null
        };
        let titles = [];
        let urls = [];
        let dates=[];

        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);
            
            for(let i=2; i<=6; i++){
                $(`#bbsForm > table > tbody > tr:nth-child(${i}) > td.title > a`).each(function(item,index,array){
                    titles.push($(this).text().trim());
                    urls.push("https://www.ygpa.or.kr/kr/ygpa/notice/"+$(this).attr("href"));
                 });
            }

            for(let i=2; i<=6; i++){
                $(`#bbsForm > table > tbody > tr:nth-child(${i}) > td.created.hidden-phone.end`).each(function(item,index,array){
                    dates.push($(this).text().trim());
                 });
            }
        }
        
        json.title = titles;
        json.url = urls;
        json.date = dates;
       
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});


module.exports = router;