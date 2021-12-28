const express = require("express");
const portmis_data=require("./portmis_data.js");
const router = express.Router();


router.get('/portuse',async(req,res,next)=>{

    try{
        let PrtAgCd=req.query.PrtAgCd;
        let PrtAgNm=req.query.PrtAgNm;
        let BeginDt=req.query.BeginDt;
        let EndDt=req.query.EndDt;
        let Clsgn=req.query.Clsgn;
        let vsslNm=req.query.vsslNm;
        let response_arr=[];

        if(PrtAgCd==''&&PrtAgNm!=''){
            PrtAgCd = await portmis_data.GetPrtAgCd(PrtAgNm);
        }
        if(Clsgn==''&&vsslNm!=''){
            Clsgn= await portmis_data.GetClsgn(vsslNm);
        }

        
        
        if(Clsgn!==''){
            for(const value of Clsgn){
                
                const result = await portmis_data.GetPortUseData(PrtAgCd,BeginDt,EndDt,value);
                response_arr.push(result);    
            }
        }else{
            const result = await portmis_data.GetPortUseData(PrtAgCd,BeginDt,EndDt,Clsgn);
            response_arr.push(result);
        }
        res.json(response_arr)
    }catch(e){
        console.error(e);
        next(e);
    }
});

router.get('/inout',async(req,res,next)=>{

    try{
        let PrtAgCd=req.query.PrtAgCd
        let PrtAgNm=req.query.PrtAgNm
        let Clsgn=req.query.Clsgn
        let vsslNm=req.query.vsslNm
        let BeginDt=req.query.BeginDt
        let EndDt=req.query.EndDt;
        let response_arr = [];
        
        if(PrtAgCd==''&&PrtAgNm!=''){
            PrtAgCd = await portmis_data.GetPrtAgCd(PrtAgNm);
        }

        if(Clsgn==''&&vsslNm!=''){
            Clsgn = await portmis_data.GetClsgn(vsslNm);                         
        }
        
        if(Clsgn!==''){   
            for(const value of Clsgn){
                
                const result = await portmis_data.GetInOutData(PrtAgCd,value,BeginDt,EndDt);
                response_arr.push(result);    
            }
        }else{
            const result = await portmis_data.GetInOutData(PrtAgCd,Clsgn,BeginDt,EndDt);
            response_arr.push(result);
        }
  
        res.json(response_arr);
    
    }catch(e){
        console.error(e);
        next(e);
    }
});

module.exports = router;