const { default: axios } = require("axios");
const cheerio = require("cheerio");
const express = require("express");
const router = express.Router();

router.get('/news',async(req,res,next)=>{

    try{
        
        //안드로이드에서 쿼리스트링을 통해 페이지번호
        process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
        let start = req.query.start;
        const response = await axios.get(
            "https://search.naver.com/search.naver"+
            "?where=news&sm=tab_pge&query=%ED%95%AD%EB%A7%8C&sort=0"+
            "&photo=0&field=0&pd=0&ds=&de=&cluster_rank=242&mynews=0&office_type=0"+
            `&office_section_code=0&news_office_checked=&nso=so:r,p:all,a:all&start=${start}`);
        
        let json ={
            image:null,
            title:null,
            url:null,
            date:null
        };
        let images =[];
        let titles = [];
        let urls = [];
        let dates =[];
    
        let selector_tag = [];
        if(response.status === 200){
            const html = response.data;
            const $ = cheerio.load(html);

            //각 뉴스 셀렉터 얻기            
            $("div.group_news > ul > li.bx").each(function(item,index,array){
               selector_tag.push("#"+$(this).attr("id"));
            });
            


            //image 얻기
            for(let tag of selector_tag){
                let selector = tag +" > div.news_wrap.api_ani_send > a > img";
                let find_selector = tag +" > div.news_wrap.api_ani_send > div";
                $(find_selector).each(function(item,index,array){
                
                    if($(selector).attr("src")){
                        images.push($(selector).attr("src"));
                    }else{
                        images.push(null);
                    }                             
                });
            }

            //title, url 얻기
            for(let tag of selector_tag){
                let selector = tag +" > div.news_wrap.api_ani_send > div > a";
                $(selector).each(function(item,index,array){
                    titles.push($(this).attr("title"));
                    urls.push($(this).attr("href"));
                });
            }

            //date얻기
            for(let tag of selector_tag){
                let selector = tag +" > div > div > div.news_info > div.info_group > span"
                $(selector).each(function(item,index,array){
                    if($(this).text().trim().includes("전")||$(this).text().trim().includes(".")) {
                        dates.push($(this).text().trim());
                    }
                });
            }
          
        }
        
        json.image= images;
        json.title = titles;
        json.url = urls;
        json.date = dates;
        
        console.log(dates.length)
        res.json(json);

    }catch(e){
        console.error(e);
        next(e);
    }
});


module.exports = router;