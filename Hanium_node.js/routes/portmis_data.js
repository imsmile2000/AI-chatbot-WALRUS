const axios = require("axios");

/*
시설사용허가현황=>
PrtAgCd:청코드(필수)
PrtAgNm:항구명
BeginDt:시작에보일자(필수)
EndDt:종료예보일자(필수)
Clsgn:호출부호
*/
const GetPortUseData = async(PrtAgCd,BeginDt,EndDt,Clsgn)=>{
    try{
        
        let result = await axios({
            method: 'POST',
            url: 'https://new.portmis.go.kr/portmis/sp/facl/fsch/selectSpFaclHrbrfUseReqstPrmisnStatusList.do',
            data:{
                "dmaParam":{
                    "srchPrtAgCd":PrtAgCd,
                    "srchPrtAgNm":"",
                    "srchBeginFrcstDt":BeginDt,
                    "srchEndFrcstDt":EndDt,
                    "srchClsgn":Clsgn,
                    "srchClsgnNm":"",
                    "srchVsslInnb":"",
                    "srchEtryptYear":"",
                    "srchEtryptCo":"",
                    "totalCount":"",
                    "currentPageNo":1,
                    "recordCount":"50000"}

            }
        })
        
        return  result.data.dltSrchList;

    }catch(error){
        console.log(error);    
    }

}
/*
선박입출항현황=>
PrtAgCd:청코드
PrtAgNm:항구명
Clsgn:호출부호
BeginDt:시작입출항일자(필수)
EndDt:종료입출항일자(필수)
*/
const GetInOutData = async(PrtAgCd,Clsgn,BeginDt,EndDt)=>{
    try{
        
        const result = await axios({
            method: 'POST',
            url: 'https://new.portmis.go.kr/portmis/sp/vssl/vsch/selectSpVsslAllPagingList.do',
            data : {
                "dmaParam":{
                    "prtAgCd":PrtAgCd,
                    "prtAgNm":"",
                    "clsgn":'',
                    "vsslNm":"",
                    "ibobprtSe":"0",
                    "etryptTkoffDt":"",
                    "bargeClsgn1":"",
                    "reqstSe":"all",
                    "bargeVsslNm":"",
                    "currentPageNo":1,
                    "recordCount":"50000",
                    "vsslInnb":Clsgn,
                    "srchBeginEtryndDt": BeginDt,
                    "srchEndEtryndDt": EndDt,
                    "entrpsCd":"",
                    "fcltyCd":"",
                    "fcltySubCd":"",
                    "fcltyNm":"",
                    "inSrchNatCd":"",
                    "befClsgn":""
                    }
                }
            
        });
        
        return result.data.dltInOutList;
    }catch(error){
        console.log(error);
    }
}

/*
선박명을 호출부호로 전환
*/
const GetClsgn = async(vsslNm)=>{
    try{
        const result=await axios({
            method: 'POST',
            url: 'https://new.portmis.go.kr/portmis/cl/vsco/vmgt/selectVsslListPop.do',
            data:{
                "dmaParam":{
                    "srchCallorNoorNm":vsslNm,
                    "srchAll":"",
                    "currentPageNo":1,
                    "recordCount":"50000",
                    "isTugboat":"",
                    "ibobprtSe":"",
                    "srchUnusedVssl":""}
            }
        });
        
        let length=result.data.gridList[0].totalCount;
        let vsslInnbList = [];

        for(let i=0;i<length;i++){
            
            if(result.data.gridList[i].vsslNm===vsslNm){
               vsslInnbList.push(result.data.gridList[i].vsslInnb);
            }
        }
        
        return vsslInnbList;
        

    }catch(error){
        console.log(error);
    }
}

/*
항구명을 청코드로 전환
*/ 
const GetPrtAgCd = async(PrtAgNm)=>{
    try{
        const result=await axios({
            method: 'POST',
            url: 'https://new.portmis.go.kr/portmis/co/como/cpop/selectCoCommPrtAgCdPopupList.do',
            data:{
                "dmaParam":{
                    "searchWord":PrtAgNm,
                    "currentPageNo":1,
                    "recordCount":50000,
                    "insttSeGubun":"",
                    "noInsttSeGubun":"",
                    "searchAllPrtSe":""}
                }
        });
        let length=result.data.gridList[0].totalCount;
        
        for(let i=0;i<length;i++){
            
            if(result.data.gridList[i].prtAgKorNm==PrtAgNm){
                
                return result.data.gridList[i].prtAgCd;
            }
            else{
                return '';
            }
        }
        

    }catch(error){
        console.log(error);
    }
}




module.exports.GetPortUseData = GetPortUseData;
module.exports.GetInOutData = GetInOutData;
module.exports.GetClsgn=GetClsgn;
module.exports.GetPrtAgCd=GetPrtAgCd;