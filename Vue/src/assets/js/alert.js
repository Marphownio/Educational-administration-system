let ALERTMSG={
    show(TH,BOOLEAN,TITLE,TYPE){
        TH.$message({
            showClose: BOOLEAN,
            message: TITLE,
            type: TYPE,
        })
    }

}
export default ALERTMSG;

