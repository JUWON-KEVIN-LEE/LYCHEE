package com.lychee.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.ActivityAddBinding
import com.lychee.ui.base.BaseActivity
import pub.devrel.easypermissions.EasyPermissions

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_add

    override val viewModelClass: Class<AddViewModel>
        get() = AddViewModel::class.java

    // private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(mBinding) {
            /*
            * TODO
            * */

            addPhotoLayout.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "사진을 선택하세요."), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
            }
            /*
            addPriceEditText
                    .textChanges()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .skip(1)
                    .filter { it.length != 1 }
                    .subscribe()
                    .addTo(mCompositeDisposable)
                    */
            /**
             * 사진 추가 버튼을 클릭하면
             * 일단 권한 요청을 합니다!
             * 관련 권한은 EXTERNAL ~~ 구글링을 해보면 나옵니다.
             *
             * 그렇게 권한 요청을 해서 성공을 하면 갤러리에 있는 사진을 가져와서 RecyclerView 에 세팅합니다.
             * 그리고 만약 권한이 승인되어있는 상태였다면 바로 갤러리에 있는 사진을 가져옵니다.
             *
             * http://superwony.tistory.com/5
             *
             * 학생들을 가르치며 보람을 느낍니다.
             * 언젠가는 한번 가르쳐 보고 싶습니다.
             * 그게 언제일까요.
             * 흠
             *
             * 어떻게 해야 되지?
             * 사진을 먼저 찍게 해야되나?
             저렇게 하면 좋을거 같다!
             0번째는 사진 찍는 버튼 그 이후로 갤러리~
             */
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            try {

            } catch (e: Exception) {

            }
        }
    }

    private fun requestPermission() {
        EasyPermissions.requestPermissions(this,
                "사진을 등록하기 위해서는 저장소에 대한 권한을 승인해주셔야 합니다.",
                EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, object: EasyPermissions.PermissionCallbacks {
            override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

            }

            override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

            }

            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

            }
        })
    }

    companion object {
        private const val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 10
    }
}
